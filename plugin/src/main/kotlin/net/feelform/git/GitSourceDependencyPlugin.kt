package net.feelform.git

import net.feelform.git.tasks.Dependency
import org.gradle.api.Plugin
import org.gradle.api.Project

class GitSourceDependencyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("git", GitExtension::class.java)
        project.tasks.register("verifyGitSourceDependency", Dependency::class.java) { task ->
            task.url.set(extension.url)
        }
    }
}