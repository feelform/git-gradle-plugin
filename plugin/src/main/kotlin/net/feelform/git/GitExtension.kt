package net.feelform.git

import org.gradle.api.provider.Property

abstract class GitExtension {
    abstract val url: Property<String>
}