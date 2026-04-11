package com.github.syunpeii.mockstation.server.service

import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.core.model.ResFileFormat
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import java.io.File

private const val DEFAULT_TEST_CASE_ID = "default"

class MockResponseResolverImpl(
    private val testCaseFileService: TestCaseFileService,
    private val settingsRepository: ServerSettingsRepository,
) : MockResponseResolver {

    override suspend fun resolve(
        testCaseId: String,
        apiPath: String,
        method: HttpMethod,
        queryParameters: Parameters,
    ): StubResponse? {
        val resFile = findResFile(testCaseId, apiPath, method, queryParameters)
            ?: if (testCaseId != DEFAULT_TEST_CASE_ID) {
                findResFile(DEFAULT_TEST_CASE_ID, apiPath, method, queryParameters)
            } else {
                null
            }

        return resFile?.let { ResFileParser.parse(it) }
    }

    private suspend fun findResFile(
        testCaseId: String,
        apiPath: String,
        method: HttpMethod,
        queryParameters: Parameters,
    ): File? {
        val testCaseDir = testCaseFileService.findTestCaseDirectory(testCaseId) ?: return null
        val settings = settingsRepository.getSettings().getOrThrow()

        val searchPatterns = buildSearchPatterns(apiPath, method, queryParameters, settings.resFileFormat)

        return searchPatterns
            .map { testCaseDir.resolve(it) }
            .firstOrNull { it.exists() && it.isFile }
    }

    private fun buildSearchPatterns(
        apiPath: String,
        method: HttpMethod,
        queryParameters: Parameters,
        format: ResFileFormat,
    ): List<String> {
        val patterns = mutableListOf<String>()
        val cleanPath = apiPath.removePrefix("/")

        when (format) {
            ResFileFormat.METHOD_SUFFIX -> {
                if (queryParameters.isEmpty()) {
                    patterns.add("$cleanPath/${method.value}.res")
                } else {
                    val paramString = queryParameters.entries()
                        .sortedBy { it.key }
                        .joinToString("--") { "${it.key}__${it.value.firstOrNull()}" }
                    patterns.add("$cleanPath/${method.value}@$paramString.res")
                    patterns.add("$cleanPath/${method.value}.res")
                }

                val pathSegments = cleanPath.split("/")
                if (pathSegments.size > 1) {
                    val withoutLastSegment = pathSegments.dropLast(1).joinToString("/")
                    patterns.add("$withoutLastSegment/${method.value}.res")
                }
            }

            ResFileFormat.SIMPLE -> {
                patterns.add("$cleanPath.res")
            }
        }

        return patterns
    }
}
