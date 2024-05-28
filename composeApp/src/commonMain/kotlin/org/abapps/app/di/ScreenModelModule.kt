package org.abapps.app.di

import org.abapps.app.presentation.app.AppScreenModel
import org.abapps.app.presentation.screens.allinvoices.AllInvoicesScreenModel
import org.abapps.app.presentation.screens.home.HomeScreenModel
import org.abapps.app.presentation.screens.posInvoiceScreen.InvoiceScreenModel
import org.abapps.app.presentation.screens.settings.SettingsScreenModel
import org.abapps.app.presentation.screens.transferInvoices.TransferInvoicesScreenModel
import org.abapps.app.presentation.screens.transferNewInvoice.TransferNewInvoiceScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val screenModelModule = module {
    factoryOf(::AppScreenModel)
    factoryOf(::AllInvoicesScreenModel)
    factoryOf(::InvoiceScreenModel)
    factoryOf(::TransferInvoicesScreenModel)
    factoryOf(::TransferNewInvoiceScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::SettingsScreenModel)
}