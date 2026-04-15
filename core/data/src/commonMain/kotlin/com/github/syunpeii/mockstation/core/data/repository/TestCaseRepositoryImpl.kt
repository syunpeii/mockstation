package com.github.syunpeii.mockstation.core.data.repository

import com.github.syunpeii.mockstation.core.data.source.local.TestCaseLocalDataSource
import com.github.syunpeii.mockstation.core.data.source.remote.TestCaseRemoteDataSource
import com.github.syunpeii.mockstation.core.model.Result
import com.github.syunpeii.mockstation.core.model.TestCase
import com.github.syunpeii.mockstation.core.model.api.ActivateTestCaseRequest
import kotlinx.coroutines.flow.Flow

class TestCaseRepositoryImpl(
    private val remoteDataSource: TestCaseRemoteDataSource,
    private val localDataSource: TestCaseLocalDataSource,
) : TestCaseRepository {

    override suspend fun getTestCases(): Result<List<TestCase>> {
        return try {
            val testCases = remoteDataSource.getTestCases()
            localDataSource.saveTestCases(testCases)
            Result.Success(testCases)
        } catch (e: Exception) {
            try {
                val cachedTestCases = localDataSource.getTestCases()
                if (cachedTestCases.isNotEmpty()) {
                    Result.Success(cachedTestCases)
                } else {
                    Result.Error(e)
                }
            } catch (_: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun getTestCase(id: String): Result<TestCase> {
        return try {
            val testCase = remoteDataSource.getTestCase(id)
            if (testCase != null) {
                localDataSource.saveTestCase(testCase)
                Result.Success(testCase)
            } else {
                val cachedTestCase = localDataSource.getTestCase(id)
                if (cachedTestCase != null) {
                    Result.Success(cachedTestCase)
                } else {
                    Result.Error(NoSuchElementException("TestCase not found: $id"))
                }
            }
        } catch (e: Exception) {
            try {
                val cachedTestCase = localDataSource.getTestCase(id)
                if (cachedTestCase != null) {
                    Result.Success(cachedTestCase)
                } else {
                    Result.Error(e)
                }
            } catch (_: Exception) {
                Result.Error(e)
            }
        }
    }

    override suspend fun createTestCase(testCase: TestCase): Result<TestCase> {
        return runCatching {
            val created = remoteDataSource.saveTestCase(testCase)
            localDataSource.saveTestCase(created)
        }.fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Error(it) },
        )
    }

    override suspend fun deleteTestCase(id: String): Result<Unit> {
        return runCatching {
            remoteDataSource.deleteTestCase(id)
            localDataSource.deleteTestCase(id)
        }.fold(
            onSuccess = { Result.Success(Unit) },
            onFailure = { Result.Error(it) },
        )
    }

    override suspend fun activateTestCase(request: ActivateTestCaseRequest): Result<Unit> {
        return runCatching {
            remoteDataSource.activateTestCase(request)
        }.fold(
            onSuccess = { Result.Success(Unit) },
            onFailure = { Result.Error(it) },
        )
    }

    override fun observeTestCases(): Flow<List<TestCase>> {
        return localDataSource.observeTestCases()
    }
}
