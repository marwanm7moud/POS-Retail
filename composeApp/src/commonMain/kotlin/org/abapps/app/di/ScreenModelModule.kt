package org.abapps.app.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.abapps.app.presentation.app.AppScreenModel
import org.abapps.app.presentation.screens.allinvoices.AllInvoicesScreenModel



val screenModelModule = module {
    factoryOf(::AppScreenModel)
    factoryOf(::AllInvoicesScreenModel)
}