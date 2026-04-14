package com.github.syunpeii.mockstation.app.di

import com.github.syunpeii.mockstation.app.ui.devicemanagement.DeviceManagementViewModel
import com.github.syunpeii.mockstation.app.ui.home.HomeViewModel
import com.github.syunpeii.mockstation.app.ui.settings.SettingsViewModel
import com.github.syunpeii.mockstation.app.ui.testcasesearch.TestCaseSearchViewModel
import com.github.syunpeii.mockstation.core.data.di.dataModule
import com.github.syunpeii.mockstation.core.database.di.databaseModule
import com.github.syunpeii.mockstation.core.datastore.di.dataStoreModule
import com.github.syunpeii.mockstation.core.domain.di.domainModule
import com.github.syunpeii.mockstation.core.network.di.networkModule
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    factory {
        SettingsViewModel(
            appSettings = get(),
            connectionSettings = get(),
            serverSettingsRepository = get(),
        )
    }

    factory {
        HomeViewModel(
            serverSettingsRepository = get(),
            deviceRepository = get(),
        )
    }

    factory {
        DeviceManagementViewModel(
            deviceRepository = get(),
            requestHistoryRepository = get(),
        )
    }

    factory {
        TestCaseSearchViewModel(
            testCaseRepository = get(),
        )
    }
}

val allModules = listOf(
    platformModule,
    networkModule,
    databaseModule,
    dataStoreModule,
    dataModule,
    domainModule,
    appModule,
)
