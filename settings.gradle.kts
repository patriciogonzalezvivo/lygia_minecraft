pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.minecraftforge.net/") {
            name = "MinecraftForge"
        }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "lygia_minecraft"
include("resourcepack")
include("fabric")
include("forge")
include("testmod")