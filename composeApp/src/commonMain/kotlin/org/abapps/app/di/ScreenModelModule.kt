package org.abapps.app.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.abapps.app.presentation.app.AppScreenModel
import org.abapps.app.presentation.screens.allinvoices.AllInvoicesScreenModel
import org.abapps.app.presentation.screens.invoiceScreen.InvoiceScreenModel
import org.abapps.app.presentation.screens.transferInvoices.TransferInvoicesScreenModel



val screenModelModule = module {
    factoryOf(::AppScreenModel)
    factoryOf(::AllInvoicesScreenModel)
    factoryOf(::InvoiceScreenModel)
    factoryOf(::TransferInvoicesScreenModel)
}