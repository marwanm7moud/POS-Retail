package org.abapps.app.presentation.screens.posInvoiceScreen

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.abapps.app.data.util.RetailSetup
import org.abapps.app.domain.entities.Customer
import org.abapps.app.domain.entities.Store
import org.abapps.app.domain.entities.User
import org.abapps.app.domain.usecase.ManageInvoiceUseCase
import org.abapps.app.domain.util.UnknownErrorException
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
                showErrorScreen = false
            )
        }
        tryToExecute(
            function = {
                getCombinedSetup()
            },
            onSuccess = { combinedSetupResult ->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorState = null,
                        errorMessage = "",
                        showErrorScreen = false,
                        stores = combinedSetupResult.stores.map { store ->
                            InvoiceDataState(
                                id = store.storeId.toLong(),
                                name = store.name
                            )
                        },
                        selectedStore = combinedSetupResult.stores.find { tempStore ->
                            tempStore.storeId == RetailSetup.STORE_ID
                        }?.toInvoiceDataState() ?: InvoiceDataState(),
                        customers = combinedSetupResult.customers.map { customer ->
                            InvoiceDataState(
                                id = customer.id,
                                name = "${customer.firstName} ${customer.lastName}"
                            )
                        },
                        selectedCustomer = combinedSetupResult.customers.find { tempCustomer ->
                            tempCustomer.id == RetailSetup.DEFAULT_CUSTOMER_ID
                        }?.toInvoiceDataState() ?: InvoiceDataState(),
                        sales = combinedSetupResult.salesPerson.map { salesPerson ->
                            InvoiceDataState(
                                id = salesPerson.id.toLong(),
                                name = salesPerson.name
                            )
                        },
                        selectedSalePerson = combinedSetupResult.salesPerson.find { tempSalesPerson ->
                            tempSalesPerson.id == RetailSetup.DEFAULT_SALES_ID
                        }?.toInvoiceDataState() ?: InvoiceDataState(),
                    )
                }
            },
            onError = ::onError
        )
    }

    private suspend fun getCombinedSetup(): CombinedSetupResult {
        return coroutineScope {
            val storesDeferred = async {
                try {
                    manageInvoice.getStores(RetailSetup.SUB_COMPANY_ID)
                } catch (e: Exception) {
                    throw UnknownErrorException(e.message.toString())
                }
            }
            val customersDeferred = async {
                try {
                    manageInvoice.getCustomers(RetailSetup.STORE_ID, RetailSetup.SUB_COMPANY_ID)
                } catch (e: Exception) {
                    throw UnknownErrorException(e.message.toString())
                }
            }
            val salesPersonsDeferred = async {
                try {
                    manageInvoice.getAllSales(RetailSetup.STORE_ID, RetailSetup.SUB_COMPANY_ID)
                } catch (e: Exception) {
                    throw UnknownErrorException(e.message.toString())
                }
            }

            val stores = storesDeferred.await()
            val customers = customersDeferred.await()
            val salesPersons = salesPersonsDeferred.await()

            CombinedSetupResult(
                stores = stores,
                customers = customers,
                salesPerson = salesPersons,
            )
        }
    }

    override fun onClickAddItem() {
        if (state.value.selectedCustomer.id != 0L) {
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
                            allItemsList = state.value.allItemsList + items.map { item ->
                                item.toUiState()
                            },
                        )
                    }
                },
                onError = ::onError
            )
        } else updateState { it.copy(errorDialogueIsVisible = true) }
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
        getSetupInvoiceData()
    }

    override fun onClickBack() {
        if (state.value.isAddItem)
            updateState {
                it.copy(
                    isAddItem = false,
                    errorMessage = "",
                    errorState = null,
                    isLoading = false
                )
            }
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

    override fun onChooseCustomer(id: Long) {
        updateState {
            it.copy(selectedCustomer = it.customers.find { f -> f.id == id } ?: InvoiceDataState())
        }
    }

    override fun onChooseStore(id: Long) {
        if (RetailSetup.IS_MAIN_STORE)
            updateState {
                it.copy(selectedStore = it.stores.find { f -> f.id == id } ?: InvoiceDataState())
            }
    }

    override fun onChooseSalesPerson(id: Long) {
        updateState {
            it.copy(selectedSalePerson = it.sales.find { f -> f.id == id } ?: InvoiceDataState())
        }
    }

    override fun onChooseInvoiceType(id: Long) {
        updateState {
            it.copy(selectedInvoiceType = it.invoiceTypes.find { f -> f.id == id }
                ?: InvoiceDataState())
        }
    }

    override fun onCommentChanged(comment: String) {
        updateState { it.copy(comment = comment) }
    }

    override fun onDismissErrorDialogue() {
        updateState { it.copy(errorDialogueIsVisible = false) }
    }

    override fun onChangeQty(qty: String, itemCode: Int) {
        updateState {
            it.copy(
                invoiceItemList = it.invoiceItemList.map { if (it.itemCode == itemCode) it.copy(qty = qty) else it }
            )
        }
    }

}


data class CombinedSetupResult(
    val stores: List<Store>,
    val customers: List<Customer>,
    val salesPerson: List<User>,
)