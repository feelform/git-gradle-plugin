package net.feelform.git

import org.gradle.api.provider.Property

abstract class SubmoduleExtension {
    abstract val url: Property<String>
    abstract val path: Property<String>
}