package com.github.syunpeii.mockstation.server.di

import com.github.syunpeii.mockstation.core.data.di.dataModule
import com.github.syunpeii.mockstation.core.database.DatabaseDriverFactory
import com.github.syunpeii.mockstation.core.database.di.databaseModule
import com.github.syunpeii.mockstation.core.datastore.di.dataStoreModule
import com.github.syunpeii.mockstation.core.domain.di.domainModule
import com.github.syunpeii.mockstation.core.network.di.networkModule
import org.koin.dsl.module

val serverPlatformModule = module {
    single<DatabaseDriverFactory> {
        TODO("Implement a DatabaseDriverFactory for the server platform")
    }
}

val serverAppModule = module {
    // TODO: Add server-specific dependencies (controllers, services, etc.)
}

val allServerModules = listOf(
    serverPlatformModule,
    networkModule,
    databaseModule,
    dataStoreModule,
    dataModule,
    domainModule,
    serverAppModule,
)
