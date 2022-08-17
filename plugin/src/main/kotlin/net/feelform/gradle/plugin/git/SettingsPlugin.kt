package net.feelform.gradle.plugin.git

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings

class SettingsPlugin : Plugin<Settings> {
    override fun apply(target: Settings) {
//        val extension = target.extensions.create<SettingsPluginExtension>("")
        target.gradle.settingsEvaluated({

        })

        target.gradle.beforeProject({ project: Project ->
            println("project $project")
        })

        target.gradle.afterProject({ project: Project ->
            println("after project: ")
        })
    }
}