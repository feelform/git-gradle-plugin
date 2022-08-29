package net.feelform.git.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class Submodule() : DefaultTask() {
    @get:Input
    abstract val url: Property<String>

    @TaskAction
    fun verify() {
        val url = this.url.get()
        logger.quiet("Successfully resolved URL '$url'")
    }
}