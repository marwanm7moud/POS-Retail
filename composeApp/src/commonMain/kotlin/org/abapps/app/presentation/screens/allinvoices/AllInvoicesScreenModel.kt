package org.abapps.app.presentation.screens.allinvoices

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import org.abapps.app.domain.usecase.ManageInvoiceUseCase
import org.abapps.app.presentation.base.BaseScreenModel
import org.abapps.app.presentation.base.ErrorState

class AllInvoicesScreenModel(
    private val manageInvoice: ManageInvoiceUseCase
) :
    BaseScreenModel<AllInvoicesState, AllInvoicesUiEffect>(AllInvoicesState()),
    AllInvoicesInteraction {


    override val viewModelScope: CoroutineScope get() = screenModelScope

    init {
        getAllInvoices()
    }

    fun getAllInvoices() {
        list.clear()
        updateState {
            it.copy(
                isLoading = true,
                errorState = null,
                errorMessage = "",
            )
        }
        tryToExecute(
            function = {
                manageInvoice.getAllInvoices()
            },
            onSuccess = { items ->
                updateState {
                    it.copy(
                        isLoading = false,
                        errorState = null,
                        errorMessage = "",
                        invoicesList = items.toUIState(),
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

    fun onClickFloatActionButton() {
        sendNewEffect(AllInvoicesUiEffect.NavigateToNewInvoice)
    }

    override fun onClickItem(index: Int) {
        updateState { it.copy(selectedInvoiceIndex = index) }
    }

    fun retry() {
        //todo
    }

    override fun showErrorScreen() {
        updateState { it.copy(showErrorScreen = true) }
    }

    override fun onClickBack() {
        sendNewEffect(AllInvoicesUiEffect.NavigateToHomeScreen)
    }
}