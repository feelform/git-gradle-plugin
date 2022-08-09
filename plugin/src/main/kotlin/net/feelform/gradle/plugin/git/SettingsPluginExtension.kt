package net.feelform.gradle.plugin.git

import org.gradle.api.provider.Property

abstract class SettingsPluginExtension {
    abstract val message: Property<String>
}