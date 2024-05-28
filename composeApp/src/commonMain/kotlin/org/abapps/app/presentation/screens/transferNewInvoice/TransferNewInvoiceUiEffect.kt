package org.abapps.app.presentation.screens.transferNewInvoice

sealed interface TransferNewInvoiceUiEffect {
    data object NavigateBackToAllInvoices : TransferNewInvoiceUiEffect
}