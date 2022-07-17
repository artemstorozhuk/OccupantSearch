plugins {
    kotlin("plugin.serialization") version "1.7.10"
    kotlin("jvm") version "1.7.10"
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
    application
}

object Versions {
    const val koin = "3.2.0"
    const val ktor = "2.0.3"
    const val koinAnnotations = "1.0.1"
    const val htmlBuilder = "1.6.8"
    const val serialization = "1.3.3"
    const val logback = "1.2.11"
    const val htmlJvm = "0.7.5"
    const val vk = "1.0.14"
    const val opencv = "4.5.1-2"
    const val argparser = "2.0.7"
    const val jsoup = "1.15.2"
    const val junit = "1.7.0"
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

dependencies {
    implementation("io.ktor:ktor-server-netty:${Versions.ktor}")
    implementation("io.ktor:ktor-serialization:${Versions.ktor}")
    implementation("io.ktor:ktor-server-content-negotiation:${Versions.ktor}")
    implementation("io.ktor:ktor-server-compression:${Versions.ktor}")
    implementation("io.ktor:ktor-server-cors:${Versions.ktor}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}")
    implementation("io.ktor:ktor-server-call-logging:${Versions.ktor}")
    implementation("io.ktor:ktor-server-status-pages:${Versions.ktor}")
    implementation("io.ktor:ktor-html-builder:${Versions.htmlBuilder}")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:${Versions.htmlJvm}")
    implementation("com.xenomachina:kotlin-argparser:${Versions.argparser}")
    implementation("ch.qos.logback:logback-classic:${Versions.logback}")
    implementation("io.insert-koin:koin-core:${Versions.koin}")
    implementation("io.insert-koin:koin-annotations:${Versions.koinAnnotations}")
    ksp("io.insert-koin:koin-ksp-compiler:${Versions.koinAnnotations}")
    implementation("com.vk.api:sdk:${Versions.vk}")
    implementation("org.openpnp:opencv:${Versions.opencv}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}")
    implementation("org.jsoup:jsoup:${Versions.jsoup}")
    testImplementation("io.ktor:ktor-server-tests-jvm:${Versions.ktor}")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Versions.junit}")
}

application {
    mainClass.set("com.occupantsearch.MainKt")
}

tasks.register<JavaExec>("devRun") {
    mainClass.set("com.occupantsearch.MainKt")
    args = listOf("--enable-cors")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<Exec>("npmInstall") {
    workingDir("../client")
    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
        commandLine("cmd.exe", "/C", "npm.cmd install")
    } else {
        commandLine = listOf("npm", "install")
    }
}

tasks.register<Exec>("npmBuild") {
    workingDir("../client")
    if (System.getProperty("os.name").toLowerCase().contains("windows")) {
        commandLine("cmd.exe", "/C", "npm.cmd run build --verbose")
    } else {
        commandLine = listOf("npm", "run", "build", "--verbose")
    }
    dependsOn(":server:npmInstall")
}

tasks.named("processResources") {
    dependsOn(":server:npmBuild")
}
