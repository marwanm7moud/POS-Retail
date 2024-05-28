package org.abapps.app.di

import org.abapps.app.data.gateway.remote.InvoiceGateway
import org.abapps.app.domain.gateway.IInvoiceGateway
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::InvoiceGateway) bind IInvoiceGateway::class
}