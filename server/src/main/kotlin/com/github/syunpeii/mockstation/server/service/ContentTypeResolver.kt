package com.github.syunpeii.mockstation.server.service

object ContentTypeResolver {
    private const val CONTENT_TYPE_JSON = "application/json"
    private const val CONTENT_TYPE_XML = "application/xml"
    private const val CONTENT_TYPE_HTML = "text/html"
    private const val CONTENT_TYPE_PLAIN = "text/plain"

    /**
     * Infers the Content\-Type from the response body content.
     *
     * Detection order:
     * 1. JSON \(starts with \{ or \[\)
     * 2. XML \(starts with \<\?xml\)
     * 3. HTML \(starts with \<\!DOCTYPE\)
     * 4. Default: text/plain
     *
     * @param body Response body
     * @return Inferred Content\-Type
     */
    fun resolve(body: String): String {
        val trimmedBody = body.trimStart()

        return when {
            trimmedBody.startsWith("{") || trimmedBody.startsWith("[") -> CONTENT_TYPE_JSON
            trimmedBody.startsWith("<?xml") -> CONTENT_TYPE_XML
            trimmedBody.startsWith("<!DOCTYPE") -> CONTENT_TYPE_HTML
            else -> CONTENT_TYPE_PLAIN
        }
    }
}
