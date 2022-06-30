plugins {
    kotlin("multiplatform") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    application
}

group = "com.occupantsearch"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/ekito/koin")
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
    maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
}

object Versions {
    const val koin = "3.2.0"
    const val ktor = "1.6.7"
    const val serialization = "1.3.3"
    const val mui = "+"
    const val react = "17.0.2-pre.290-kotlin-1.6.10"
    const val logback = "1.2.11"
    const val htmlJvm = "0.7.2"
    const val vk = "1.0.14"
    const val opencv = "4.5.1-2"
}

fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target:${Versions.mui}"

object Deps {

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
    }

}
kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Deps.Koin) {
                    api(core)
                    api(test)
                }
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("ch.qos.logback:logback-classic:${Versions.logback}")
                implementation("io.ktor:ktor-server-core:${Versions.ktor}")
                implementation("io.ktor:ktor-server-netty:${Versions.ktor}")
                implementation("io.ktor:ktor-html-builder:${Versions.ktor}")
                implementation("io.ktor:ktor-serialization:${Versions.ktor}")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:${Versions.htmlJvm}")
                implementation("com.vk.api:sdk:${Versions.vk}")
                implementation("org.openpnp:opencv:${Versions.opencv}")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:${Versions.react}")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:${Versions.react}")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-css:${Versions.react}")
                implementation("io.ktor:ktor-client-json:${Versions.ktor}")
                implementation("io.ktor:ktor-client-serialization:${Versions.ktor}")

                implementation(kotlinw("react"))
                implementation(kotlinw("react-dom"))
                implementation(kotlinw("react-router-dom"))

                implementation(kotlinw("emotion"))
                implementation(kotlinw("mui"))
                implementation(kotlinw("mui-icons"))

                implementation(npm("chart.js", "3.8.0"))
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("com.occupantsearch.MainKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}