plugins {
    id("multiloader-common")
    id("net.neoforged.moddev")
}

neoForge {
    val mod_id = commonMod.prop("mod_id")
    val minecraft_version = project.name
    val commonProject = project(":common")

    neoFormVersion = commonMod.prop("neo_form_version")

    // Use version-specific AccessTransformer
    val at = commonProject.file("src/main/resources/accesstransformers/$minecraft_version-$mod_id.cfg")
    if (!at.exists()) {
        throw GradleException("Failed to locate src/main/resources/accesstransformers/$minecraft_version-$mod_id.cfg")
    }
    accessTransformers.from(at.absolutePath)

    parchment {
        minecraftVersion = commonMod.prop("parchment_minecraft")
        mappingsVersion = commonMod.prop("parchment_version")
    }
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.5")

    // fabric and neoforge both bundle mixinextras, so it is safe to use it in common
    compileOnly("io.github.llamalad7:mixinextras-common:0.3.5")
    annotationProcessor("io.github.llamalad7:mixinextras-common:0.3.5")

    // ASM for bytecode manipulation
    compileOnly("org.ow2.asm:asm-tree:9.6")

    // Common library dependencies
    compileOnly("io.noties:prism4j:2.0.0")
    compileOnly("org.jooq:joor:0.9.15")
    compileOnly("com.neovisionaries:nv-websocket-client:2.14")
    compileOnly("org.javassist:javassist:3.30.2-GA")
}

val commonJava by configurations.creating {
    isCanBeResolved = false
    isCanBeConsumed = true
}

val commonResources by configurations.creating {
    isCanBeResolved = false
    isCanBeConsumed = true
}

// Get the Stonecutter-generated sources directory
val stonecutterGeneratedJava = layout.buildDirectory.dir("generated/stonecutter/main/java")
val stonecutterGeneratedResources = layout.buildDirectory.dir("generated/stonecutter/main/resources")

artifacts {
    // Use the Stonecutter-generated sources after processing
    add(commonJava.name, stonecutterGeneratedJava) {
        builtBy("stonecutterGenerate")
    }
    add(commonResources.name, stonecutterGeneratedResources) {
        builtBy("stonecutterGenerate")
    }
}

tasks.named("createMinecraftArtifacts") {
    val mcVersion = project.name  // The project name is the minecraft version (e.g., "1.21.8")
    dependsOn(":common:${mcVersion}:stonecutterGenerate")
}

stonecutter {
    replacements.string(current.parsed >= "1.21.11") {
        replace("ResourceLocation", "Identifier")

        // Conflicts
        replace("parseIdentifier", "parseIdentifier")
        replace("getAdvancementsForIdentifiers", "getAdvancementsForIdentifiers")
        replace("suggestIdentifier", "suggestIdentifier")
        replace("mapIdentifiers", "mapIdentifiers")
        replace("getIdentifier", "getIdentifier")
        replace("writeIdentifier", "writeIdentifier")
        replace("readIdentifier", "readIdentifier")
        replace("getWorldIdentifier", "getWorldIdentifier")
        replace("base.readResourceLocation", "base.readIdentifier")
        replace("base.writeResourceLocation", "base.writeIdentifier")
        replace("@return the raw minecraft Identifier.", "@return the raw minecraft Identifier.")
    }
}
