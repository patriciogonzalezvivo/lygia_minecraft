import xyz.lygia.Versions

plugins {
    id("xyz.lygia.default")
    id("xyz.lygia.loader")
    id("net.minecraftforge.gradle") version ("[6.0,6.2)")
}

evaluationDependsOn(":resourcepack")

minecraft {
    mappings("official", Versions.MINECRAFT)
    copyIdeResources.set(true)
    runs {
        create("client") {
            taskName("Client")
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            mods {
                create("test_mod") {
                    source(sourceSets.main.get())
                }
            }
        }
    }
}

dependencies {
    "minecraft"("net.minecraftforge:forge:${Versions.MINECRAFT}-${Versions.FORGE}")
}

tasks {
    processResources {
        include("assets/minecraft/shaders/core/*")
    }
}
