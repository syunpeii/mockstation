package com.github.syunpeii.mockstation.server.service

import com.github.syunpeii.mockstation.core.model.TestCaseDetail
import com.github.syunpeii.mockstation.core.model.TestCaseSummary
import java.io.File

interface TestCaseFileService {
    suspend fun getTestCaseRootDir(): File
    suspend fun findAllTestCases(): List<TestCaseSummary>
    suspend fun findTestCaseById(id: String): TestCaseDetail?
    suspend fun findTestCaseDirectory(testCaseId: String): File?
}
