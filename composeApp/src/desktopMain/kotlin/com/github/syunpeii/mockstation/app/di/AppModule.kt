package com.github.syunpeii.mockstation.app.di

import com.github.syunpeii.mockstation.app.ui.testcase.TestCaseViewModel
import com.github.syunpeii.mockstation.core.data.di.dataModule
import com.github.syunpeii.mockstation.core.database.di.databaseModule
import com.github.syunpeii.mockstation.core.datastore.di.dataStoreModule
import com.github.syunpeii.mockstation.core.domain.di.domainModule
import com.github.syunpeii.mockstation.core.network.di.networkModule
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    factory {
        TestCaseViewModel(
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
