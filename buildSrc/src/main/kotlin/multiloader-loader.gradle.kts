import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.api.tasks.bundling.Jar
import org.gradle.language.jvm.tasks.ProcessResources

plugins {
    id("multiloader-common")
}

val mod_id: String by project

val commonJava by configurations.creating {
    isCanBeResolved = true
    // canBeConsumed stays default (false), same as your Groovy
}

val commonResources by configurations.creating {
    isCanBeResolved = true
}

dependencies {
    compileOnly(project(":common")) {
        capabilities {
            requireCapability("${project.group}:$mod_id")
        }
    }

    add(commonJava.name, project(mapOf("path" to ":common", "configuration" to "commonJava")))
    add(commonResources.name, project(mapOf("path" to ":common", "configuration" to "commonResources")))
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn(commonJava)
    source(commonJava)
}

tasks.named<ProcessResources>("processResources") {
    dependsOn(commonResources)
    from(commonResources)
}

tasks.named<Javadoc>("javadoc") {
    dependsOn(commonJava)
    source(commonJava)
}

tasks.named<Jar>("sourcesJar") {
    dependsOn(commonJava)
    from(commonJava)

    dependsOn(commonResources)
    from(commonResources)
}
