package net.feelform.gradle.plugin.git

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class DefaultHttpCaller : HttpCaller {
    override fun get(url: String): HttpResponse {
        try {
            val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
            connection.connectTimeout = 5000
            connection.requestMethod = "GET"
            connection.connect()

            val code = connection.responseCode
            val message = connection.responseMessage
            return HttpResponse(code, message)
        } catch (e: IOException) {
            throw HttpCallException("Failed to call URL $url via HTTP GET", e)
        }
    }
}