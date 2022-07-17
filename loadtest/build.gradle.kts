plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "com.occupantsearch.loadtest"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

application {
    mainClass.set("com.occupantsearch.loadtest.LoadTestKt")
}
