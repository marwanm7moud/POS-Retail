package org.abapps.app.presentation.screens.posInvoiceScreen

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import org.abapps.app.data.util.RetailSetup
import org.abapps.app.domain.usecase.ManageInvoiceUseCase
import org.abapps.app.presentation.base.BaseScreenModel

class InvoiceScreenModel(
    private val manageInvoice: ManageInvoiceUseCase
) :
    BaseScreenModel<NewInvoiceUiState, InvoiceUiEffect>(NewInvoiceUiState()), InvoiceInteractions {

    override val viewModelScope: CoroutineScope get() = screenModelScope

    override fun onClickAddItem() {
        updateState { it.copy(isAddItem = true) }
        tryToExecute(
            function = {
                manageInvoice.getAllItems(
                    RetailSetup.STORE_ID,
                    RetailSetup.SUB_COMPANY_ID,
                    RetailSetup.DEFAULT_CUSTOMER_ID,
                    RetailSetup.FIFO || RetailSetup.AVERAGE
                )
            },
            onSuccess = { items ->
                updateState {
                    it.copy(
                        allItemsList = items.map { item ->
                            item.toUiState()
                        },
                    )
                }
            },
            onError = {
                println(it.toString())
            }
        )
    }

    override fun onClickDone() {
        updateState { invoice ->
            invoice.copy(
                isAddItem = false,
                invoiceItemList = invoice.selectedItemsIndexFromAllItems.map {
                    invoice.allItemsList.get(
                        it
                    )
                }.map { it.toInvoiceItemUiState() },
                selectedItemsIndexFromAllItems = emptyList()
            )
        }
    }

    fun retry() {

    }

    override fun onClickBack() {
        if (state.value.isAddItem)
            updateState { it.copy(isAddItem = false) }
        else
            sendNewEffect(InvoiceUiEffect.NavigateBackToAllInvoices)
    }

    override fun onClickItemFromAllItems(index: Int) {
        if (state.value.selectedItemsIndexFromAllItems.contains(index))
            updateState { it.copy(selectedItemsIndexFromAllItems = state.value.selectedItemsIndexFromAllItems - index) }
        else
            updateState { it.copy(selectedItemsIndexFromAllItems = state.value.selectedItemsIndexFromAllItems + index) }
    }

    override fun onClickItemFromInvoice(index: Int) {
        updateState { it.copy(selectedItemIndexFromInvoice = index) }
    }

    override fun onClickExpandedCard(expandedCardStatus: ExpandedCardStatus) {
        if (state.value.expandedCardStatus == expandedCardStatus)
            updateState { it.copy(expandedCardStatus = null) }
        else
            updateState { it.copy(expandedCardStatus = expandedCardStatus) }
    }

    override fun onClickItemDelete(index: Int) {
        updateState { it.copy(invoiceItemList = it.invoiceItemList - it.invoiceItemList[index]) }
    }

    override fun showErrorScreen() {
        updateState { it.copy(showErrorScreen = true) }
    }

}