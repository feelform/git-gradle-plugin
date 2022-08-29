package net.feelform.git.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class SubmoduleUpdater : DefaultTask() {
    @get:Input
    abstract val url: Property<String>

    @get:Input
    abstract val relativePath: Property<String>

    @TaskAction
    fun update() {
        val url = this.url.get()
        val path = this.relativePath.get()
        logger.quiet("Successfully resolved URL '$url' path '$path'")
    }
}