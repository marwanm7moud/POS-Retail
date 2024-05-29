package org.abapps.app.presentation.screens.posInvoiceScreen

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import org.abapps.app.data.util.RetailSetup
import org.abapps.app.domain.usecase.ManageInvoiceUseCase
import org.abapps.app.presentation.base.BaseScreenModel
import org.abapps.app.presentation.base.ErrorState

class InvoiceScreenModel(
    private val manageInvoice: ManageInvoiceUseCase,
) : BaseScreenModel<NewInvoiceUiState, InvoiceUiEffect>(NewInvoiceUiState()), InvoiceInteractions {

    override val viewModelScope: CoroutineScope get() = screenModelScope

    init {
        getSetupInvoiceData()
    }

    private fun getSetupInvoiceData() {
        updateState {
            it.copy(
                isLoading = true,
                errorState = null,
                errorMessage = "",
            )
        }
        tryToExecute(
            function = {
                manageInvoice.getStores(RetailSetup.SUB_COMPANY_ID)
            },
            onSuccess = { stores ->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorState = null,
                        errorMessage = "",
                        stores = stores.map { store ->
                            InvoiceDataState(
                                id = store.storeId,
                                name = store.name
                            )
                        },
                        selectedStore = stores.find { tempStore ->
                            tempStore.storeId == RetailSetup.STORE_ID
                        }?.toInvoiceDataState() ?: InvoiceDataState()
                    )
                }
            },
            onError = ::onError
        )
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
                        isLoading = false,
                        errorState = null,
                        errorMessage = "",
                        allItemsList = items.map { item ->
                            item.toUiState()
                        },
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

    override fun onClickDone() {
        updateState { invoice ->
            invoice.copy(
                isAddItem = false,
                invoiceItemList = invoice.selectedItemsIndexFromAllItems.map {
                    invoice.allItemsList[it]
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

    override fun onChooseCustomer(id: Int) {
        updateState { it.copy(selectedCustomer = it.selectedCustomer.copy(id = id)) }
    }

    override fun onChooseStore(id: Int) {
        if (RetailSetup.IS_MAIN_STORE)
            updateState { it.copy(selectedStore = it.selectedStore.copy(id = id)) }
    }

    override fun onChooseSalesPerson(id: Int) {
        updateState { it.copy(selectedSalePerson = it.selectedSalePerson.copy(id = id)) }
    }

    override fun onChooseInvoiceType(id: Int) {
        updateState { it.copy(selectedInvoiceType = it.selectedInvoiceType.copy(id = id)) }
    }

    override fun onCommentChanged(comment: String) {
        updateState { it.copy(comment = comment) }
    }

}