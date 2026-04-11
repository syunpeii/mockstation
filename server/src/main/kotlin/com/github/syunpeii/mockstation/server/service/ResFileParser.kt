package com.github.syunpeii.mockstation.server.service

import io.ktor.http.HttpStatusCode
import java.io.File

object ResFileParser {
    private const val SECTION_STATUS = "status"
    private const val SECTION_HEADER = "header"
    private const val SECTION_BODY = "body"
    private const val HEADER_SEPARATOR = ":"
    private const val HEADER_SEPARATOR_LIMIT = 2
    private const val SECTION_BRACKET_OPEN = "["
    private const val SECTION_BRACKET_CLOSE = "]"

    fun parse(file: File): StubResponse {
        val content = file.readText()
        val sections = parseSections(content)

        val status = sections[SECTION_STATUS]?.trim()?.toIntOrNull()?.let {
            HttpStatusCode.fromValue(it)
        } ?: HttpStatusCode.OK

        val headers = sections[SECTION_HEADER]?.lines()
            ?.mapNotNull { line ->
                val parts = line.split(HEADER_SEPARATOR, limit = HEADER_SEPARATOR_LIMIT)
                if (parts.size == HEADER_SEPARATOR_LIMIT) parts[0].trim() to parts[1].trim() else null
            }
            ?.toMap() ?: emptyMap()

        val body = sections[SECTION_BODY] ?: ""

        return StubResponse(status, headers, body)
    }

    private fun parseSections(content: String): Map<String, String> {
        val sections = mutableMapOf<String, String>()
        var currentSection: String? = null
        val currentContent = StringBuilder()

        content.lines().forEach { line ->
            when {
                line.startsWith(SECTION_BRACKET_OPEN) &&
                    line.endsWith(SECTION_BRACKET_CLOSE) -> {
                    currentSection?.let {
                        sections[it] = currentContent.toString().trim()
                    }

                    currentSection = line.substring(
                        SECTION_BRACKET_OPEN.length,
                        line.length - SECTION_BRACKET_CLOSE.length,
                    )
                    currentContent.clear()
                }

                else -> {
                    if (currentContent.isNotEmpty()) currentContent.appendLine()
                    currentContent.append(line)
                }
            }
        }

        currentSection?.let {
            sections[it] = currentContent.toString().trim()
        }

        return sections
    }
}

data class StubResponse(
    val status: HttpStatusCode,
    val headers: Map<String, String>,
    val body: String,
)
