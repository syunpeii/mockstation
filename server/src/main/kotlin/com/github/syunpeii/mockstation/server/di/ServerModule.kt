package com.github.syunpeii.mockstation.server.di

import com.github.syunpeii.mockstation.core.data.di.dataModule
import com.github.syunpeii.mockstation.core.data.repository.DeviceRepository
import com.github.syunpeii.mockstation.core.data.repository.DeviceRepositoryImpl
import com.github.syunpeii.mockstation.core.data.repository.ServerSettingsRepository
import com.github.syunpeii.mockstation.core.datastore.di.dataStoreModule
import com.github.syunpeii.mockstation.core.domain.di.domainModule
import com.github.syunpeii.mockstation.core.network.api.DeviceApi
import com.github.syunpeii.mockstation.core.network.di.networkModule
import com.github.syunpeii.mockstation.data.repository.RequestHistoryRepository
import com.github.syunpeii.mockstation.data.repository.RequestHistoryRepositoryImpl
import com.github.syunpeii.mockstation.server.repository.ServerSettingsRepositoryImpl
import com.github.syunpeii.mockstation.server.service.DeviceService
import com.github.syunpeii.mockstation.server.service.DeviceServiceImpl
import com.github.syunpeii.mockstation.server.service.MockResponseResolver
import com.github.syunpeii.mockstation.server.service.MockResponseResolverImpl
import com.github.syunpeii.mockstation.server.service.RequestHistoryService
import com.github.syunpeii.mockstation.server.service.RequestHistoryServiceImpl
import com.github.syunpeii.mockstation.server.service.TestCaseFileService
import com.github.syunpeii.mockstation.server.service.TestCaseFileServiceImpl
import io.ktor.server.config.ApplicationConfig
import org.koin.dsl.module

val serverAppModule = module {
    single<DeviceRepository> {
        DeviceRepositoryImpl(
            deviceApi = get<DeviceApi>(),
        )
    }

    single<ServerSettingsRepository> {
        ServerSettingsRepositoryImpl(
            applicationConfig = get<ApplicationConfig>(),
        )
    }

    single<TestCaseFileService> {
        TestCaseFileServiceImpl(
            settingsRepository = get<ServerSettingsRepository>(),
        )
    }

    single<MockResponseResolver> {
        MockResponseResolverImpl(
            testCaseFileService = get<TestCaseFileService>(),
            settingsRepository = get<ServerSettingsRepository>(),
        )
    }

    single<DeviceService> {
        DeviceServiceImpl(get<DeviceRepository>())
    }

    single<RequestHistoryRepository> {
        RequestHistoryRepositoryImpl()
    }

    single<RequestHistoryService> {
        RequestHistoryServiceImpl(get<RequestHistoryRepository>())
    }
}

val allServerModules = listOf(
    networkModule,
    dataStoreModule,
    dataModule,
    domainModule,
    serverAppModule,
)
