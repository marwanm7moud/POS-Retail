package org.abapps.app.di

import org.abapps.app.data.gateway.remote.InvoiceGateway
import org.abapps.app.data.gateway.remote.SetupGateway
import org.abapps.app.data.gateway.remote.pagesource.InvoicesPagingSource
import org.abapps.app.data.gateway.remote.pagesource.ItemsPagingSource
import org.abapps.app.domain.gateway.IInvoiceGateway
import org.abapps.app.domain.gateway.ISetupGateway
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val GatewayModule = module {
    singleOf(::InvoiceGateway) bind IInvoiceGateway::class
    singleOf(::SetupGateway) bind ISetupGateway::class
    singleOf(::ItemsPagingSource)
    singleOf(::InvoicesPagingSource)
}