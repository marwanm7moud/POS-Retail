package org.abapps.app.presentation.screens.transferNewInvoice

import androidx.paging.PagingData
import androidx.paging.map
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.abapps.app.data.util.RetailSetup
import org.abapps.app.domain.usecase.CalculationInvoiceUseCase
import org.abapps.app.domain.usecase.ManageInvoiceUseCase
import org.abapps.app.presentation.base.BaseScreenModel
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.presentation.screens.posInvoiceScreen.ItemUiState

class TransferNewInvoiceScreenModel(
    private val manageInvoice: ManageInvoiceUseCase,
    private val calculationInvoice: CalculationInvoiceUseCase,
) : BaseScreenModel<TransferNewInvoiceUiState, TransferNewInvoiceUiEffect>(TransferNewInvoiceUiState()),
    TransferNewInvoiceInteractions {

    override val viewModelScope: CoroutineScope get() = screenModelScope
    override fun onChangeQty(text: String, itemID: Long) {
        updateState {
            it.copy(
                invoiceItemList = it.invoiceItemList.map { item ->
                    if (item.itemID == itemID) item.copy(qtyTran = text) else item
                }
            )
        }
        val newCalc = calculationInvoice.calculateTransfer(
            state.value.invoiceItemList,
            state.value.calculations
        )
        updateState { it.copy(calculations = newCalc) }
    }

    override fun onChangeCommentInItem(text: String, itemID: Long) {
        updateState {
            it.copy(
                invoiceItemList = it.invoiceItemList.map { item ->
                    if (item.itemID == itemID) item.copy(comment = text) else item
                }
            )
        }
    }

    override fun onClickAddItem() {
        updateState {
            it.copy(
                isAddItem = true,
                isLoading = true,
                errorState = null,
                errorMessage = "",
            )
        }
        tryToExecute(
            function = {
                manageInvoice.getAllItems(RetailSetup.DEFAULT_CUSTOMER_ID)
            },
            onSuccess = { items ->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorState = null,
                        errorMessage = "",
                        allItemsList = items.toUIState(),
                    )
                }
            },
            onError = ::onError
        )

    }

    private fun onError(error: ErrorState) {
        updateState {
            it.copy(
                errorState = error,
                isLoading = false,
                showErrorScreen = true,
                errorMessage = when (error) {
                    is ErrorState.UnknownError -> error.message.toString()
                    is ErrorState.ServerError -> error.message.toString()
                    is ErrorState.NotFound -> error.message.toString()
                    is ErrorState.ValidationNetworkError -> error.message.toString()
                    is ErrorState.NetworkError -> error.message.toString()
                    else -> "Something wrong happened please try again !"
                }
            )
        }
    }

    override fun onDismissErrorDialogue() {
        updateState { it.copy(errorDialogueIsVisible = false) }
    }

    override fun onChangeTransferNumber(text: String) {
        updateState { it.copy(transferNumber = text) }
    }

    override fun onChangeComment(text: String) {
        updateState { it.copy(comment = text) }
    }


    override fun onChangeTranstype(text: String) {
        updateState { it.copy(transType = text) }
    }

    override fun onChooseFromStore(index: Long) {
        updateState {
            it.copy(selectedFromStore = it.fromStoreOptions.find { f -> f.id == index }
                ?: TransferDataState())
        }
    }

    override fun onChooseToStore(index: Long) {
        updateState {
            it.copy(selectedToStore = it.toStoreOptions.find { f -> f.id == index }
                ?: TransferDataState())
        }
    }

    override fun onChooseStatus(index: Long) {
        updateState {
            it.copy(selectedStatus = it.statusOptions.find { f -> f.id == index }
                ?: TransferDataState())
        }
    }

    override fun onClickDone() {
        viewModelScope.launch(Dispatchers.Default) {
            if (RetailSetup.ALLOW_NEGATIVE_QTY && list
                    .any { it.onHand <= 0 }
            ) {
                val updatedInvoicesItem = addInvoices(
                    state.value.invoiceItemList,
                    state.value.selectedItemsIndexFromAllItems.map {
                        list[it]
                    }.map { it.toTransferNewInvoiceItemUiState() })
                updateState { invoice ->
                    invoice.copy(
                        isAddItem = false,
                        invoiceItemList = updatedInvoicesItem,
                        selectedItemsIndexFromAllItems = emptyList(),
                        calculations = calculationInvoice.calculateTransfer(
                            updatedInvoicesItem, state.value.calculations
                        )
                    )
                }
            } else updateState {
                it.copy(
                    errorDialogueIsVisible = true,
                    errorMessage = "Negative onHand is not allowed"
                )
            }
        }
//        updateState { invoice ->
//            invoice.copy(
//                isAddItem = false,
//                invoiceItemList = invoice.selectedItemsIndexFromAllItems.map {
//                    invoice.allItemsList.get(
//                        it
//                    )
//                }.map { it.toInvoiceItemUiState() },
//                selectedItemsIndexFromAllItems = emptyList()
//            )
        //}
    }

    private fun addInvoices(
        currentInvoices: List<TransferNewInvoiceItemUiState>,
        newInvoices: List<TransferNewInvoiceItemUiState>
    ): List<TransferNewInvoiceItemUiState> {
        val updatedInvoices = currentInvoices.toMutableList()
        for (newInvoice in newInvoices) {
            val existingInvoice = updatedInvoices.find { it.itemID == newInvoice.itemID }
            if (existingInvoice != null) {
                existingInvoice.qtyTran = (existingInvoice.qtyTran.toFloat() + 1f).toString()
            } else {
                updatedInvoices.add(newInvoice)
            }
        }

        return updatedInvoices
    }

    private fun Flow<PagingData<ItemUiState>>.toListBlocking(): List<ItemUiState> {
        val itemList = mutableListOf<ItemUiState>()
        viewModelScope.launch(Dispatchers.Default) {
            this@toListBlocking.collect { pagingData ->
                pagingData.map { itemList.add(it) }
            }
        }
        return itemList
    }

    fun retry() {
        //todo
    }

    override fun onClickBack() {
        if (state.value.isAddItem)
            updateState { it.copy(isAddItem = false) }
        else
            sendNewEffect(TransferNewInvoiceUiEffect.NavigateBackToAllInvoices)
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