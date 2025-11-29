package com.github.syunpeii.mockstation.core.testing.fake

import com.github.syunpeii.mockstation.core.data.repository.TestCaseRepository
import com.github.syunpeii.mockstation.core.model.Result
import com.github.syunpeii.mockstation.core.model.TestCase
import com.github.syunpeii.mockstation.core.model.TestCaseStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Clock

class FakeTestCaseRepository : TestCaseRepository {

    private val testCases = mutableListOf<TestCase>()

    override suspend fun getTestCases(): Result<List<TestCase>> {
        return Result.Success(testCases.toList())
    }

    override suspend fun getTestCase(id: String): Result<TestCase> {
        val testCase = testCases.find { it.id == id }
        return if (testCase != null) {
            Result.Success(testCase)
        } else {
            Result.Error(NoSuchElementException("TestCase not found: $id"))
        }
    }

    override suspend fun createTestCase(testCase: TestCase): Result<TestCase> {
        testCases.add(testCase)
        return Result.Success(testCase)
    }

    override suspend fun deleteTestCase(id: String): Result<Unit> {
        testCases.removeIf { it.id == id }
        return Result.Success(Unit)
    }

    override fun observeTestCases(): Flow<List<TestCase>> {
        return flowOf(testCases.toList())
    }

    fun addSampleData() {
        val now = Clock.System.now()
        testCases.addAll(
            listOf(
                TestCase(
                    id = "1",
                    title = "Sample Test Case 1",
                    description = "This is a sample test case",
                    status = TestCaseStatus.PENDING,
                    createdAt = now,
                    updatedAt = now
                ),
                TestCase(
                    id = "2",
                    title = "Sample Test Case 2",
                    description = "This is another sample test case",
                    status = TestCaseStatus.IN_PROGRESS,
                    createdAt = now,
                    updatedAt = now
                )
            )
        )
    }

    /**
     * Clear test data
     */
    fun clear() {
        testCases.clear()
    }
}
