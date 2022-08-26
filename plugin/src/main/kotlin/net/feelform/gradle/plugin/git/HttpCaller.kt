package net.feelform.gradle.plugin.git

interface HttpCaller {
    fun get(url: String): HttpResponse
}