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

    @TaskAction
    fun update() {
        val url = this.url.get()
        val path = this.destinationDir.get()
        val submoduleHashFile = File(project.layout.projectDirectory.asFile, ".gitsubmodule")

        val hash = getLatestHash(url)
        val hashFromDestinationDir = getHashFromFile(submoduleHashFile)

        if (hash != hashFromDestinationDir) {
            if (hash != null) {
                writeFile(submoduleHashFile, hash)
                if (project.rootProject.layout.projectDirectory.asFile.absolutePath != project.layout.projectDirectory.asFile.absolutePath) {
                    logger.quiet("rootProject exists ${project.rootProject.layout.projectDirectory}")
                    getExecOperations()!!.exec { spec: ExecSpec ->
                        spec.commandLine("git", "submodule", "update", "--init", "--recursive")
                        spec.workingDir = project.rootProject.layout.projectDirectory.asFile
                    }
                }
            }
        }
        logger.quiet("hash '$hash' hashFromDestinationDir '$hashFromDestinationDir'")
        logger.quiet("Successfully resolved URL '$url' path '$path' submodule file '$submoduleHashFile'")
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

    private fun getLatestHash(url: String): String? {
        val directory = project.layout.projectDirectory.asFile
        val output = ByteArrayOutputStream()
        val result: ExecResult = getExecOperations()!!.exec(Action { spec: ExecSpec ->
            spec.commandLine("git", "ls-remote", url)
            spec.isIgnoreExitValue = true
            spec.standardOutput = output
            spec.workingDir = directory
        })
        if (result.exitValue != 0) {
            return null
        }
        val outputString = output.toString().trim { it <= ' ' }.split("\\s+".toRegex())
        if (outputString.isEmpty()) {
            return null
        }
        return outputString[0]
    }

    private fun getHashFromFile(file: File): String? {
        if (!file.exists()) {
            return null
        }
        return file.readText(Charsets.UTF_8)

    }
}