package com.github.syunpeii.mockstation.app.di

import app.cash.sqldelight.db.SqlDriver
import com.github.syunpeii.mockstation.core.database.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

val platformModule: Module = module {
    single<DatabaseDriverFactory> {
        object : DatabaseDriverFactory {
            override fun createDriver(): SqlDriver {
                TODO("Implement JVM-specific SqlDriver creation")
            }
        }
    }
}
