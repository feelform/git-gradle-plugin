package net.feelform.git

import net.feelform.git.tasks.Submodule
import org.gradle.api.Plugin
import org.gradle.api.Project

class GitSubmodulePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("submodule", GitExtension::class.java)
        project.tasks.register("verifyGitSourceDependency", Submodule::class.java) { task ->
            task.url.set(extension.url)
        }
    }
}