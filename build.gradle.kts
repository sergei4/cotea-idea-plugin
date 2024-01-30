plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.7.21"
    id("org.jetbrains.intellij") version "1.15.0"
    id("org.hidetake.ssh") version "2.10.1"
}

group = "pro.mobicode.mobile.ide.util"

apply( from = "./gradleSrc/release.gradle")

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.1.1")
    type.set("IC") // Target IDE Platform
    plugins.set(listOf("Kotlin", "android"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_11.toString()
        targetCompatibility = JavaVersion.VERSION_11.toString()
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    }

    patchPluginXml {
        sinceBuild.set("221")
        untilBuild.set("233.*")
    }

}
