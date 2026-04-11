package com.github.syunpeii.mockstation.server.di

import com.github.syunpeii.mockstation.core.data.di.dataModule
import com.github.syunpeii.mockstation.core.data.repository.DeviceRepository
import com.github.syunpeii.mockstation.core.data.repository.DeviceRepositoryImpl
import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepositoryImpl
import com.github.syunpeii.mockstation.core.datastore.di.dataStoreModule
import com.github.syunpeii.mockstation.core.domain.di.domainModule
import com.github.syunpeii.mockstation.core.network.di.networkModule
import com.github.syunpeii.mockstation.server.service.DeviceService
import com.github.syunpeii.mockstation.server.service.DeviceServiceImpl
import com.github.syunpeii.mockstation.server.service.MockResponseResolver
import com.github.syunpeii.mockstation.server.service.MockResponseResolverImpl
import com.github.syunpeii.mockstation.server.service.TestCaseFileService
import com.github.syunpeii.mockstation.server.service.TestCaseFileServiceImpl
import org.koin.dsl.module

val serverAppModule = module {
    single<DeviceRepository> {
        DeviceRepositoryImpl()
    }

    single<ServerSettingsRepository> {
        ServerSettingsRepositoryImpl()
    }

    single<TestCaseFileService> {
        TestCaseFileServiceImpl(
            settingsRepository = get(),
        )
    }

    single<MockResponseResolver> {
        MockResponseResolverImpl(
            testCaseFileService = get(),
            settingsRepository = get(),
        )
    }

    single<DeviceService> {
        DeviceServiceImpl(get<DeviceRepository>())
    }
}

val allServerModules = listOf(
    networkModule,
    dataStoreModule,
    dataModule,
    domainModule,
    serverAppModule,
)
