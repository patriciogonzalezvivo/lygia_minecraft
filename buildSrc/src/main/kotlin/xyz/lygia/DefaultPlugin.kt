package xyz.lygia

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.tasks.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.withType
import org.gradle.language.jvm.tasks.ProcessResources
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.set
import kotlin.io.path.Path

class DefaultPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        setupDefaults(project)
        applyJavaPlugin(project)
    }

    private fun setupDefaults(project: Project) {
        project.plugins.apply(BasePlugin::class.java)
        // Sets the name of the built jar
        project.extensions.getByType(BasePluginExtension::class.java).archivesName.set("${Properties.NAME}-${project.name.lowercase(Locale.getDefault())}-${Versions.MINECRAFT}")
        project.version = Versions.MOD
        project.group = Properties.GROUP
    }

    private fun applyJavaPlugin(project: Project) {
        project.plugins.apply(JavaLibraryPlugin::class.java)
        with(project.extensions.getByType(JavaPluginExtension::class.java)) {
            toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.majorVersion))
        }

        project.tasks {
            withType<JavaCompile> {
                this.options.encoding = StandardCharsets.UTF_8.toString()
                this.options.release.set(Versions.JAVA.toInt())
            }
            @Suppress("UnstableApiUsage") withType<ProcessResources> {
                this.includeEmptyDirs = false
                val values = mapOf(
                        "version" to project.version,
                        "minecraft_version" to Versions.MINECRAFT,
                        "mod_id" to Properties.MODID,
                        "mod_name" to Properties.NAME,
                        "git_repo" to Properties.GIT_REPO,
                        "homepage" to Properties.HOMEPAGE,
                        "description" to Properties.DESCRIPTION,
                        "license" to Properties.LICENSE,
                        "resourcepack_version" to Versions.RESOURCEPACK
                )
                inputs.properties(values)
                include(
                        "**/*.glsl", "**/logo.png", "**/.mcmeta", "fabric.mod.json", "META-INF/mods.toml", "pack.mcmeta"
                )
                filesMatching(setOf("fabric.mod.json", "META-INF/mods.toml", "pack.mcmeta")) {
                    expand(values)
                }
                eachFile {
                    if (!this.path.contains("META-INF")) {
                        this.path = this.path.lowercase()
                    }
                }
                val includeDir = Path("assets", "minecraft", "shaders", "include")
                filesMatching("**/*.glsl") {
                    filter { line ->
                        if (line.contains("#include")) {
                            val split =
                                    line.replace("#include", "#moj_import").lowercase(Locale.getDefault()).split("\"")
                            val absolutePath =
                                    includeDir.relativize(Path(this.path).parent.resolve(split[1]).normalize())
                            return@filter "${split[0]}<${absolutePath.joinToString(separator = "/")}>"
                        }
                        line
                    }
                }
            }
            withType<Jar>().configureEach {
                manifest {
                    attributes["Specification-Title"] = Properties.NAME
                    attributes["Specification-Vendor"] = Properties.AUTHOR
                    attributes["Specification-Version"] = archiveVersion
                    attributes["Implementation-Title"] = project.name
                    attributes["Implementation-Version"] = archiveVersion
                    attributes["Implementation-Vendor"] = Properties.AUTHOR
                    attributes["Built-On-Java"] =
                            "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})"
                    attributes["Built-On-Minecraft"] = Versions.MINECRAFT
                }
            }
        }

    }

}
