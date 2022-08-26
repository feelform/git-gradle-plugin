package net.feelform.git

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings

class GitSourceDependencyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("git", GitExtension::class.java)
    }
}