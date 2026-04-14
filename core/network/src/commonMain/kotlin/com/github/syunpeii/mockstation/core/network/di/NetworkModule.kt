package com.github.syunpeii.mockstation.core.network.di

import com.github.syunpeii.mockstation.core.network.HttpClientFactory
import com.github.syunpeii.mockstation.core.network.api.DeviceApi
import com.github.syunpeii.mockstation.core.network.api.DeviceApiImpl
import com.github.syunpeii.mockstation.core.network.api.RequestHistoryApi
import com.github.syunpeii.mockstation.core.network.api.RequestHistoryApiImpl
import com.github.syunpeii.mockstation.core.network.api.ServerApi
import com.github.syunpeii.mockstation.core.network.api.ServerApiImpl
import com.github.syunpeii.mockstation.core.network.api.TestCaseApi
import com.github.syunpeii.mockstation.core.network.api.TestCaseApiImpl
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    single<HttpClient> {
        HttpClientFactory.create(
            enableLogging = true,
        )
    }

    single<TestCaseApi> {
        TestCaseApiImpl(
            client = get(),
            baseUrl = "http://localhost:8080",
        )
    }

    single<ServerApi> {
        ServerApiImpl(
            client = get(),
            baseUrl = "http://localhost:8080",
        )
    }

    single<DeviceApi> {
        DeviceApiImpl(
            client = get(),
            baseUrl = "http://localhost:8080",
        )
    }

    single<RequestHistoryApi> {
        RequestHistoryApiImpl(
            client = get(),
            baseUrl = "http://localhost:8080",
        )
    }
}
