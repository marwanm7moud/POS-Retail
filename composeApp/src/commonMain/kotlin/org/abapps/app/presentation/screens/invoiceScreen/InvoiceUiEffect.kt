package org.abapps.app.presentation.screens.invoiceScreen

sealed interface InvoiceUiEffect {
    data object NavigateBackToAllInvoices : InvoiceUiEffect
}