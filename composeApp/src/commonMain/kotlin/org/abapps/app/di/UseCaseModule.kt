package org.abapps.app.di

import org.abapps.app.domain.usecase.AdminSystemUseCase
import org.abapps.app.domain.usecase.ManageInvoiceUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::ManageInvoiceUseCase)
    singleOf(::AdminSystemUseCase)
}