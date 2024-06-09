package org.abapps.app.presentation.screens.posInvoiceScreen

import androidx.paging.map
import app.cash.paging.PagingData
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.abapps.app.data.util.RetailSetup
import org.abapps.app.domain.entities.Customer
import org.abapps.app.domain.entities.Discount
import org.abapps.app.domain.entities.Store
import org.abapps.app.domain.entities.User
import org.abapps.app.domain.usecase.CalculationInvoiceUseCase
import org.abapps.app.domain.usecase.ManageInvoiceUseCase
import org.abapps.app.domain.util.UnknownErrorException
import org.abapps.app.presentation.base.BaseScreenModel
import org.abapps.app.presentation.base.ErrorState

class InvoiceScreenModel(
    private val manageInvoice: ManageInvoiceUseCase,
    private val calculationInvoice: CalculationInvoiceUseCase,
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
                        discounts = state.value.discounts + combinedSetupResult.discounts.map { discount ->
                            DiscountDataState(
                                id = discount.discId.toLong(),
                                name = discount.name,
                                type = discount.discType,
                                value = discount.value
                            )
                        },
                        selectedInvoiceDiscount = DiscountDataState()
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

            val discountsDeferred = async {
                try {
                    manageInvoice.getAllDiscounts(RetailSetup.SUB_COMPANY_ID)
                } catch (e: Exception) {
                    throw UnknownErrorException(e.message.toString())
                }
            }

            val stores = storesDeferred.await()
            val customers = customersDeferred.await()
            val salesPersons = salesPersonsDeferred.await()
            val discounts = discountsDeferred.await()

            CombinedSetupResult(
                stores = stores,
                customers = customers,
                salesPerson = salesPersons,
                discounts = discounts,
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

    override fun onClickDone() {
        viewModelScope.launch(Dispatchers.Default) {
            if (RetailSetup.ALLOW_NEGATIVE_QTY && state.value.allItemsList.toListBlocking()
                    .any { it.onHand <= 0 }
            ) {
                updateState { invoice ->
                    invoice.copy(
                        isAddItem = false,
                        invoiceItemList = addInvoices(
                            invoice.invoiceItemList,
                            invoice.selectedItemsIndexFromAllItems.map {
                                invoice.allItemsList.toListBlocking()[it]
                            }.map { it.toInvoiceItemUiState() }),
                        selectedItemsIndexFromAllItems = emptyList(),
                    )
                }
            } else updateState {
                it.copy(
                    errorDialogueIsVisible = true,
                    errorMessage = "Negative onHand is not allowed"
                )
            }
        }
    }

    private fun addInvoices(
        currentInvoices: List<NewInvoiceItemUiState>,
        newInvoices: List<NewInvoiceItemUiState>
    ): List<NewInvoiceItemUiState> {
        val updatedInvoices = currentInvoices.toMutableList()
        for (newInvoice in newInvoices) {
            val existingInvoice = updatedInvoices.find { it.itemID == newInvoice.itemID }
            if (existingInvoice != null) {
                existingInvoice.qty = (existingInvoice.qty.toFloat() + 1f).toString()
            } else {
                updatedInvoices.add(newInvoice)
            }
        }
        return calculationInvoice.calculateItemsPrice(updatedInvoices).also {
            val newCalc = calculationInvoice.calculateInvoice(
                it, state.value.calculations
            )
            updateState { u ->
                u.copy(calculations = newCalc)
            }
        }
    }

    private fun Flow<PagingData<ItemUiState>>.toListBlocking(): List<ItemUiState> {
        val itemList = mutableListOf<ItemUiState>()
        viewModelScope.launch(Dispatchers.Default) {
            this@toListBlocking.map { pagingData ->
                pagingData.map {
                    itemList.add(it)
                }
            }
        }
        return itemList
    }


    fun retry() {
        updateState { it.copy(showErrorScreen = false) }
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

    override fun onClickItemDelete(itemId: Long) {
        updateState { it.copy(invoiceItemList = it.invoiceItemList - it.invoiceItemList.find { item -> item.itemID == itemId }!!) }
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

    override fun onChooseDiscount(id: Long) {
        val discount = state.value.discounts.find { f -> f.id == id }
            ?: DiscountDataState()
        updateState {
            it.copy(
                selectedInvoiceDiscount = discount,
                calculations = it.calculations.copy(discountAmount = discount.value)
            )
        }
        val newCalc = calculationInvoice.calculateInvoice(
            state.value.invoiceItemList,
            state.value.calculations
        )
        updateState { it.copy(calculations = newCalc) }
    }

    override fun onChooseDiscountItem(id: Long) {
        val discount = state.value.discounts.find { f -> f.id == id }
            ?: DiscountDataState()
        updateState {
            it.copy(
                selectedItemDiscount = discount,
                calculationItem = it.calculations.copy(discountAmount = discount.value)
            )
        }
    }

    override fun onClickItemDiscount(itemId: Long) {
        updateState {
            it.copy(showDiscountDialog = true, itemId = itemId)
        }
    }

    override fun onDismissSettingsDialogue() {
        updateState {
            it.copy(showDiscountDialog = false)
        }
    }

    override fun onClickOkInDiscountDialog() {
        val newList = calculationInvoice.calculateItemDiscount(
            state.value.invoiceItemList,
            state.value.calculationItem,
            state.value.itemId
        )
        val newCalc = calculationInvoice.calculateInvoice(
            newList,
            state.value.calculations
        )
        updateState {
            it.copy(
                calculations = newCalc,
                showDiscountDialog = false,
                invoiceItemList = newList,
                itemId = 0L
            )
        }
    }

    override fun onChangeQty(qty: String, itemId: Long) {
        updateState {
            it.copy(
                invoiceItemList = it.invoiceItemList.map { item ->
                    if (item.itemID == itemId) item.copy(qty = qty) else item
                }
            )
        }
        val newList = calculationInvoice.calculateItemsPrice(state.value.invoiceItemList)
        val newCalc = calculationInvoice.calculateInvoice(newList, state.value.calculations)
        updateState { it.copy(invoiceItemList = newList, calculations = newCalc) }
    }

    override fun onChangeDiscount(discountAmount: String) {
        if (discountAmount.isNotBlank()) {
            updateState {
                it.copy(
                    calculations = it.calculations.copy(
                        discountAmount = discountAmount.toFloat()
                    )
                )
            }
            val newCalc = calculationInvoice.calculateInvoice(
                state.value.invoiceItemList,
                state.value.calculations
            )
            updateState { it.copy(calculations = newCalc) }
        }
    }

    override fun onChangeDiscountItem(discountAmount: String) {
        if (discountAmount.isNotBlank()) {
            updateState {
                it.copy(
                    calculationItem = it.calculationItem.copy(
                        discountAmount = discountAmount.toFloat()
                    )
                )
            }
        }
    }
}


data class CombinedSetupResult(
    val stores: List<Store>,
    val customers: List<Customer>,
    val salesPerson: List<User>,
    val discounts: List<Discount>,
)