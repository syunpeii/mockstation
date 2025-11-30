package com.github.syunpeii.mockstation.core.domain.usecase

import com.github.syunpeii.mockstation.core.data.repository.TestCaseRepository
import com.github.syunpeii.mockstation.core.model.Result
import com.github.syunpeii.mockstation.core.model.TestCase

class CreateTestCaseUseCase(
    private val repository: TestCaseRepository,
) {
    suspend operator fun invoke(testCase: TestCase): Result<TestCase> {
        if (testCase.title.isBlank()) {
            return Result.Error(IllegalArgumentException("Title cannot be blank"))
        }

        return repository.createTestCase(testCase)
    }
}
