package org.abapps.app.presentation.screens.transferInvoices

import org.abapps.app.presentation.base.ErrorState

data class TransferInvoicesState(
    val errorMessage: String = "",
    val errorState: ErrorState? = null,
    val isLoading: Boolean = false,
    val showErrorScreen: Boolean = false,
    val invoicesList: List<TInvoiceUiState> = listOf(
        TInvoiceUiState(),
        TInvoiceUiState(),
        TInvoiceUiState(),
        TInvoiceUiState(),
    ),
    val selectedInvoiceIndex: Int = -1, )

data class TInvoiceUiState(
    val fromStore: String = "city stars",
    val toStore: String = "main store",
    val transType: String = "Transfer",
    val transDate: String = "2013/12/6",
    val status: String = "received",
    val comment: String = "",
    val isReceived: Boolean = true,
    val isHold: Boolean = true,
    val isPost: Boolean = true,
    val createDate: String = "",
    val totQtyTran: Long =61,
    val sentByUser: String ="karim",
    val ws: Long =61,
    val indexId: Long =61,
)
