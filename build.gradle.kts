plugins {
    kotlin("jvm") version "1.9.0" // Use the appropriate Kotlin version
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test")) // Adds kotlin-test dependenczy
}

tasks.test {
    useJUnitPlatform() // Configures the test task to use JUnit Platform
}