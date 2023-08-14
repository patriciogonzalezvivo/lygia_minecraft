plugins {
    id("xyz.lygia.default")
    java
}

tasks {
    jar {
        archiveExtension.set("zip")
    }
}