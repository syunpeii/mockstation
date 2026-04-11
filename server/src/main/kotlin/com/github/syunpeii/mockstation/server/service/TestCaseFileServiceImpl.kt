package com.github.syunpeii.mockstation.server.service

import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.core.model.TestCaseDetail
import com.github.syunpeii.mockstation.core.model.TestCaseSummary
import java.io.File

class TestCaseFileServiceImpl(
    private val settingsRepository: ServerSettingsRepository,
) : TestCaseFileService {

    override suspend fun getTestCaseRootDir(): File {
        val settings = settingsRepository.getSettings().getOrThrow()

        return File(expandPath(settings.testCaseDirectory)).apply {
            if (!exists()) mkdirs()
        }
    }

    override suspend fun findAllTestCases(): List<TestCaseSummary> {
        val rootDir = getTestCaseRootDir()
        val testCases = mutableListOf<TestCaseSummary>()

        rootDir.listFiles { file -> file.isDirectory }?.forEach { dir ->
            val summary = parseTestCaseSummary(dir)
            if (summary != null) testCases.add(summary)
        }

        rootDir.resolve(CASE_SUBDIRECTORY).takeIf { it.exists() }?.listFiles { file -> file.isDirectory }?.forEach { dir ->
            val summary = parseTestCaseSummary(dir)
            if (summary != null) testCases.add(summary)
        }

        return testCases
    }

    override suspend fun findTestCaseById(id: String): TestCaseDetail? {
        val dir = findTestCaseDirectory(id) ?: return null
        return parseTestCaseDetail(dir)
    }

    override suspend fun findTestCaseDirectory(testCaseId: String): File? {
        val rootDir = getTestCaseRootDir()

        val topLevelDir = rootDir.resolve(testCaseId)
        if (topLevelDir.exists() && topLevelDir.isDirectory) {
            return topLevelDir
        }

        val groupedDir = rootDir.resolve("$CASE_SUBDIRECTORY$PATH_SEPARATOR$testCaseId")
        return groupedDir.takeIf { it.exists() && it.isDirectory }
    }

    private fun parseTestCaseSummary(dir: File): TestCaseSummary? {
        if (!dir.isDirectory) return null

        val readmeFile = dir.resolve(README_FILE_NAME)
        val description = if (readmeFile.exists()) {
            readmeFile.readLines().take(README_PREVIEW_LINES).joinToString(" ")
        } else {
            ""
        }

        return TestCaseSummary(
            id = dir.name,
            title = dir.name,
            description = description,
            tags = emptyList(),
        )
    }

    private fun parseTestCaseDetail(dir: File): TestCaseDetail {
        val readmeFile = dir.resolve(README_FILE_NAME)
        val readme = if (readmeFile.exists()) readmeFile.readText() else ""

        val resFiles = findAllResFiles(dir)

        return TestCaseDetail(
            id = dir.name,
            title = dir.name,
            description = readme,
            files = resFiles.map { it.relativeTo(dir).path },
            tags = emptyList(),
        )
    }

    private fun findAllResFiles(dir: File): List<File> {
        val resFiles = mutableListOf<File>()
        dir.walkTopDown().forEach { file ->
            if (file.isFile && file.extension == RES_FILE_EXTENSION) {
                resFiles.add(file)
            }
        }

        return resFiles
    }

    private fun expandPath(path: String): String = if (path.startsWith(HOME_PATH_PREFIX)) {
        val userHome = System.getProperty(USER_HOME_PROPERTY)
        path.replaceFirst(HOME_PATH_PREFIX, userHome)
    } else {
        path
    }

    companion object {
        private const val CASE_SUBDIRECTORY = "case"
        private const val README_FILE_NAME = "README.md"
        private const val RES_FILE_EXTENSION = "res"
        private const val README_PREVIEW_LINES = 3
        private const val PATH_SEPARATOR = "/"
        private const val HOME_PATH_PREFIX = "~"
        private const val USER_HOME_PROPERTY = "user.home"
    }
}
