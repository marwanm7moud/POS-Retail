package org.abapps.app.di

import org.koin.dsl.module

val AppModule = module(createdAtStart = true) {
    includes(NetworkModule, screenModelModule, UseCaseModule, GatewayModule)
}