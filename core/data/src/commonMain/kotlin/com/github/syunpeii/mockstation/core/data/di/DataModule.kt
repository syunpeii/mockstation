package com.github.syunpeii.mockstation.core.data.di

import com.github.syunpeii.mockstation.core.data.repository.DeviceRepository
import com.github.syunpeii.mockstation.core.data.repository.DeviceRepositoryImpl
import com.github.syunpeii.mockstation.core.data.repository.TestCaseRepository
import com.github.syunpeii.mockstation.core.data.repository.TestCaseRepositoryImpl
import com.github.syunpeii.mockstation.core.data.source.local.TestCaseLocalDataSource
import com.github.syunpeii.mockstation.core.data.source.remote.TestCaseRemoteDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule: Module = module {
    single<TestCaseLocalDataSource> {
        TestCaseLocalDataSource()
    }

    single<TestCaseRemoteDataSource> {
        TestCaseRemoteDataSource(
            api = get(),
        )
    }

    single<TestCaseRepository> {
        TestCaseRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get(),
        )
    }

    single<DeviceRepository> {
        DeviceRepositoryImpl()
    }
}
