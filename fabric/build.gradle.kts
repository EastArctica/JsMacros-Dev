import org.gradle.language.jvm.tasks.ProcessResources
import java.io.File

plugins {
    id("multiloader-loader")
    id("fabric-loom")
}

base {
    archivesName.set("${property("mod_id")}-${property("minecraft_version")}-fabric-${project.version}")
}

// Configuration for embedding extension jars
val extensionJars by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

// Gradle is stupid and will throw a `Type mismatch: inferred type is Dependency? but Any was expected` otherwise
fun DependencyHandlerScope.implInclude(notation: Any) {
    add("implementation", requireNotNull(include(notation)))
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")

    mappings(
        loom.layered {
            officialMojangMappings()
            parchment(
                "org.parchmentmc.data:parchment-${property("parchment_minecraft")}:${property("parchment_version")}@zip"
            )
        }
    )

    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")

    // ModMenu integration
    modImplementation("com.terraformersmc:modmenu:15.0.0")

    // Common library dependencies - include for bundling in jar
    implInclude("io.noties:prism4j:2.0.0")
    implInclude("org.jooq:joor:0.9.15")
    implInclude("com.neovisionaries:nv-websocket-client:2.14")
    implInclude("org.javassist:javassist:3.30.2-GA")

    // Extension jars to embed
    add(extensionJars.name, project(mapOf("path" to ":extension:graal", "configuration" to "archives")))
    add(extensionJars.name, project(mapOf("path" to ":extension:graal:js", "configuration" to "archives")))
}

// Collect extension jar names for dependencies property
fun getExtensionJarPaths(): String =
    extensionJars.files.joinToString(", ") { file ->
        "\"META-INF/jsmacrosdeps/${file.name}\""
    }

tasks.named<ProcessResources>("processResources") {
    // Embed extension jars into the final jar
    dependsOn(extensionJars)
    from(extensionJars) {
        into("META-INF/jsmacrosdeps")
    }

    // Add dependencies expansion for jsmacros.extension.json
    filesMatching("jsmacros.extension.json") {
        expand(mapOf("dependencies" to getExtensionJarPaths()))
    }
}

loom {
    val modId = property("mod_id").toString()
    val mcVersion = property("minecraft_version").toString()

    val aw = project(":common:${mcVersion}").file("src/main/resources/$modId.accesswidener")
    if (aw.exists()) {
        accessWidenerPath.set(aw)
    }

    mixin {
        defaultRefmapName.set("$modId.refmap.json")
    }

    runs {
        named("client") {
            client()
            setConfigName("Fabric Client")
            ideConfigGenerated(true)
            runDir("runs/client")
        }
    }
}
