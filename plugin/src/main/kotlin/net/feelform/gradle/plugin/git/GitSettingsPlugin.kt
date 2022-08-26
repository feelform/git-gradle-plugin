package net.feelform.gradle.plugin.git

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class GitSettingsPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {
        val extension = settings.extensions.create("git", GitExtension::class.java)
    }
}