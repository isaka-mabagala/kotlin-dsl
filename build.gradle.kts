plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-cio:3.0.0-beta-1")
    implementation("io.ktor:ktor-server-html-builder:3.0.0-beta-1")
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.11.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}