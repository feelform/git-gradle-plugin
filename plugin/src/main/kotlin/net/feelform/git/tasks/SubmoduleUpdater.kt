package net.feelform.git.tasks

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import org.gradle.process.ExecResult
import org.gradle.process.ExecSpec
import java.io.*
import javax.inject.Inject


abstract class SubmoduleUpdater : DefaultTask() {
    // end::git-clone[]
    @Inject
    protected abstract fun getExecOperations(): ExecOperations?

    @get:Input
    abstract val url: Property<String>

    @get:OutputDirectory
    abstract val destinationDir: DirectoryProperty

    @get:Input
    abstract val submoduleTag: Property<String>

    @TaskAction
    fun update() {
        val url = this.url.get()
        val path = this.destinationDir.get()
        val submoduleTagFile = File(project.layout.projectDirectory.asFile, ".gitsubmodule")
        writeFile(submoduleTagFile, submoduleTag.get())

        isCheckoutSubmodule(url)

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

    private fun isCheckoutSubmodule(url: String): Boolean {
        val directory = project.layout.projectDirectory.asFile
        if (!directory.isDirectory) {
            return false
        }
        val output = ByteArrayOutputStream()
        val result: ExecResult = getExecOperations()!!.exec(Action { spec: ExecSpec ->
            spec.commandLine("git", "ls-remote", url)
            spec.isIgnoreExitValue = true
            spec.standardOutput = output
            spec.workingDir = directory
        })
        if (result.exitValue != 0) {
            return false
        }
        val outputString = output.toString().trim { it <= ' ' }.split("\\s+".toRegex())
        logger.quiet(outputString[0])
        return result.exitValue == 0 && url == outputString[0]
    }
}