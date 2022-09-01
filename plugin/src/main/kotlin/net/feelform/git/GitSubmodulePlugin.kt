package net.feelform.git

import net.feelform.git.tasks.SubmoduleUpdater
import org.gradle.api.Plugin
import org.gradle.api.Project

class GitSubmodulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("submodule", SubmoduleExtension::class.java)
        project.tasks.register("updateSubmodule", SubmoduleUpdater::class.java) { task ->
            task.url.set(extension.url)
            task.destinationDir.set(project.layout.projectDirectory.dir(extension.path))
        }
    }
}