package org.abapps.app.presentation.screens.posInvoiceScreen

sealed interface InvoiceUiEffect {
    data object NavigateBackToAllInvoices : InvoiceUiEffect
}