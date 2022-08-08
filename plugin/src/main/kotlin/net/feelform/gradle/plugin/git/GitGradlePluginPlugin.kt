package net.feelform.gradle.plugin.git

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * A simple 'hello world' plugin.
 */
class GitGradlePluginPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        // Register a task
        project.tasks.register("greeting") { task ->
            task.doLast {
                println("Hello from plugin 'net.feelform.git.gradle.plugin.greeting'")
            }
        }
    }
}