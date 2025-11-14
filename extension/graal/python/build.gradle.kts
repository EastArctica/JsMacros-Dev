plugins {
    java
}

val graal_version: String by project.properties

dependencies {
    implementation(libs.graal.python)
    implementation(libs.graal.llvm)

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.jetbrains:annotations:20.1.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}