package org.abapps.app.presentation.screens.allinvoices

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import org.abapps.app.domain.entities.Invoice
import org.abapps.app.presentation.base.ErrorState

data class AllInvoicesState(
    val errorMessage: String = "",
    val errorState: ErrorState? = null,
    val isLoading: Boolean = false,
    val showErrorScreen: Boolean = false,
    val invoicesList: Flow<PagingData<InvoiceUiState>> = emptyFlow(),
    val selectedInvoiceIndex: Int = -1

)

val list = mutableListOf<InvoiceUiState>()

data class InvoiceUiState(
    val invId: Int = 100006,
    val invNumber: Int = 100006,
    val invType: String = "cc",
    val status: String = "cc",
    val sourceType: String = "cc",
    val comment: String = "cc",
    val hold: Boolean = true,
    val invDate: String = "cc",
    val post: Boolean = true,
    val postedDate: String = "cc",
    val custId: Int = 100006,
    val firstName: String = "cc",
    val cashierId: Int = 100006,
    val cashierName: String = "cc",
    val amt: String = "cc",
    val createDate: String = "cc",
    val sComId: Int = 1,
    val storeId: Int = 1,
    val ws: Int = 1,
    val soId: Int = 0,
    val reverse: Int = 0,
    val indexId: Int = 0,
)

fun Invoice.toUiState(): InvoiceUiState {
    return InvoiceUiState(
        invId = invcId,
        invNumber = invcNo,
        invType = invcType,
        status = status,
        sourceType = sourceType,
        comment = comment,
        hold = hold,
        invDate = invcDate,
        post = post,
        postedDate = postedDate,
        custId = custId,
        firstName = firstName,
        cashierId = cashierId,
        cashierName = cashierName,
        amt = amount.toString(),
        createDate = createDate,
        sComId = scomId,
        storeId = storeId,
        ws = ws,
        soId = soId,
        reverse = reverse,
        indexId = -indexId,
    )
}

fun Flow<PagingData<Invoice>>.toUIState(): Flow<PagingData<InvoiceUiState>> {
    return this.map { pagingData ->
        pagingData.map {
            list.add(it.toUiState())
            it.toUiState()
        }
    }
}