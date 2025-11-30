package com.github.syunpeii.mockstation.core.network

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object HttpClientFactory {

    /**
     * Creates and configures an instance of HttpClient.
     *
     * @param baseUrl The base URL for the HTTP client.
     * @param enableLogging Flag to enable or disable logging. Default is true.
     * @return Configured HttpClient instance.
     */
    fun create(
        baseUrl: String = "http://localhost:8080",
        enableLogging: Boolean = true,
    ): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                )
            }

            if (enableLogging) {
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.INFO
                }
            }

            expectSuccess = true
        }
    }
}
