package net.feelform.gradle.plugin.git

import org.gradle.api.tasks.Nested

abstract class GitExtension {
    @Nested
    abstract fun getFetch(): FetchExtension
}