pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("http://192.168.2.149:8081/repository/mobile/")
            isAllowInsecureProtocol = true
        }
    }
}
rootProject.name = "cotea-idea-plugin"