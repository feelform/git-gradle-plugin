package net.feelform.git.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException


abstract class SubmoduleUpdater : DefaultTask() {
    @get:Input
    abstract val url: Property<String>

    @get:Input
    abstract val relativePath: Property<String>

    @get:OutputFile
    abstract val submoduleTag: Property<String>

    @TaskAction
    fun update() {
        val url = this.url.get()
        val path = this.relativePath.get()
        val submoduleTagFile = project.layout.projectDirectory.asFile
        writeFile(File(submoduleTagFile, ".gitsubmodule"), submoduleTag.get())

        logger.quiet("Successfully resolved URL '$url' path '$path' submodule file '$submoduleTagFile'")
    }

    @Throws(IOException::class)
    private fun writeFile(destination: File, content: String) {
        var output: BufferedWriter? = null
        try {
            output = BufferedWriter(FileWriter(destination))
            output.write(content)
        } finally {
            output?.close()
        }
    }
}