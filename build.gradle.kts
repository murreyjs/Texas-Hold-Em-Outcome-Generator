plugins {
    kotlin("jvm") version "1.9.0" // Use the appropriate Kotlin version
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test")) // Adds kotlin-test dependenczy
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")
}

tasks.test {
    useJUnitPlatform() // Configures the test task to use JUnit Platform
}