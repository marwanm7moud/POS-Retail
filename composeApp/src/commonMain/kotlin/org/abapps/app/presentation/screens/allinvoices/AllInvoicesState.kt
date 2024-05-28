package org.abapps.app.presentation.screens.allinvoices

import org.abapps.app.presentation.base.ErrorState

data class AllInvoicesState(
    val errorMessage: String = "",
    val errorState: ErrorState? = null,
    val isLoading: Boolean = false,
    val invoicesList: List<InvoiceUiState> = listOf(
        InvoiceUiState(),
        InvoiceUiState(),
        InvoiceUiState(),
        InvoiceUiState(),
    ),
    val selectedInvoiceIndex: Int = -1,

    )

data class InvoiceUiState(
    val invId: Long = 100006,
    val invNumber: Long = 100006,
    val invType: String = "cc",
    val status: String = "cc",
    val sourceType: String = "cc",
    val comment: String = "cc",
    val hold: Boolean = true,
    val invDate: String = "cc",
    val post: Boolean = true,
    val postedDate: String = "cc",
    val custId: Long = 100006,
    val firstName: String = "cc",
    val cashierId: Long = 100006,
    val cashierName: String = "cc",
    val amt: String = "cc",
    val createDate: String = "cc",
    val sComId: Long = 1,
    val storeId: Long = 1,
    val ws: Long = 1,
    val soId: Long = 0,
    val reverse: Long = 0,
    val indexId: Long = 0,
)