package net.feelform.git.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class Dependency(@get:Input val url: Property<String>) : DefaultTask() {

    @TaskAction
    fun verify() {
        logger.
    }
}