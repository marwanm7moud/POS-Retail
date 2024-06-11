package org.abapps.app.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.abapps.app.util.getHttpClientEngine
import org.koin.dsl.module

val NetworkModule = module(createdAtStart = true) {
    single(createdAtStart = true) {
        val client = HttpClient(getHttpClientEngine()) {
            expectSuccess = true
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }
            defaultRequest {
                header("Content-Type", "application/json")
                url("http://192.168.0.102:9898/")
                // url(get<ILocalConfigurationGateway>().getApiUrl())
            }
        }
        //authorizationIntercept(client)
        client
    }
}