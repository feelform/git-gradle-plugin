package net.feelform.git

import net.feelform.git.tasks.Dependency
import org.gradle.api.Plugin
import org.gradle.api.Project

class GitSourceDependencyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("git", GitExtension::class.java)
        val task = project.tasks.create("cloneGitSourceDependency", Dependency::class.java, extension.url)
    }
}