package org.abapps.app.presentation.screens.allinvoices

sealed interface AllInvoicesUiEffect {
    data object NavigateToNewInvoice : AllInvoicesUiEffect
    data object NavigateToHomeScreen : AllInvoicesUiEffect
}