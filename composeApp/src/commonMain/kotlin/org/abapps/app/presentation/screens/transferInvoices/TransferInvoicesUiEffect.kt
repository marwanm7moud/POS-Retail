package org.abapps.app.presentation.screens.transferInvoices

sealed interface TransferInvoicesUiEffect {
    data object NavigateToNewInvoice : TransferInvoicesUiEffect
    data object NavigateToHomeScreen : TransferInvoicesUiEffect
}