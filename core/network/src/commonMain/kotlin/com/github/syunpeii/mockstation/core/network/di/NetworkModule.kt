package com.github.syunpeii.mockstation.core.network.di

import com.github.syunpeii.mockstation.core.network.HttpClientFactory
import com.github.syunpeii.mockstation.core.network.api.TestCaseApi
import com.github.syunpeii.mockstation.core.network.api.TestCaseApiImpl
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    single<HttpClient> {
        HttpClientFactory.create()
    }
    single<TestCaseApi> {
        TestCaseApiImpl(
            client = get(),
        )
    }
}
