package com.github.syunpeii.mockstation.core.database.di

import com.github.syunpeii.mockstation.core.database.DatabaseDriverFactory
import com.github.syunpeii.mockstation.core.database.MockstationDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: Module = module {
    single<MockstationDatabase> {
        val driver = get<DatabaseDriverFactory>().createDriver()
        MockstationDatabase(driver)
    }
}
