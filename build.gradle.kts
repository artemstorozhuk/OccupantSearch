allprojects {
    group = "com.occupantsearch"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
    }
}
