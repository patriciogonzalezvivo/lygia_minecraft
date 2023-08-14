plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    gradleApi()
}

gradlePlugin {
    plugins {
        create("default") {
            id = "xyz.lygia.default"
            implementationClass = "xyz.lygia.DefaultPlugin"
        }
        create("loader") {
            id = "xyz.lygia.loader"
            implementationClass = "xyz.lygia.LoaderPlugin"
        }
    }
}
