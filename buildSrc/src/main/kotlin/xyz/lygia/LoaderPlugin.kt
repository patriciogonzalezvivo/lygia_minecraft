package xyz.lygia

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.gradle.language.jvm.tasks.ProcessResources

class LoaderPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        afterEvaluate {
            project.tasks {
                @Suppress("UnstableApiUsage")
                withType<ProcessResources> {
                    from(rpJava(project).sourceSets.getByName("main").resources)
                }
            }
        }
    }

    private fun rp(project: Project): Project {
        return project.project(":resourcepack")
    }

    private fun rpJava(project: Project): JavaPluginExtension {
        return rp(project).extensions.getByType(JavaPluginExtension::class.java)
    }

}