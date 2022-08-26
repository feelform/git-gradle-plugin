package net.feelform.gradle.plugin.git

class HttpResponse(private val code: Int, private val message: String) {
    override fun toString(): String {
        return "HTTP $code, Reason: $message"
    }

    fun getCode(): Int {
        return code
    }

    fun getMessage(): String {
        return message
    }
}