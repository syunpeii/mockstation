package com.github.syunpeii.mockstation.core.domain.di

import com.github.syunpeii.mockstation.core.domain.usecase.CreateTestCaseUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule: Module = module {
    factory<CreateTestCaseUseCase> {
        CreateTestCaseUseCase(
            repository = get(),
        )
    }
}
