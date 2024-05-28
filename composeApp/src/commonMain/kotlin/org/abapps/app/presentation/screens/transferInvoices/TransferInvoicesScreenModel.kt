package org.abapps.app.presentation.screens.transferInvoices

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import org.abapps.app.presentation.base.BaseScreenModel

class TransferInvoicesScreenModel() :
    BaseScreenModel<TransferInvoicesState, TransferInvoicesUiEffect>(TransferInvoicesState()),
    TransferInvoicesInteraction {


    override val viewModelScope: CoroutineScope get() = screenModelScope

    fun onClickFloatActionButton() {
        sendNewEffect(TransferInvoicesUiEffect.NavigateToNewInvoice)
    }

    override fun onClickItem(index: Int) {
        updateState { it.copy(selectedInvoiceIndex = index) }
    }
    override fun onClickItemDelete(index: Int) {
        updateState { it.copy(invoicesList = it.invoicesList - it.invoicesList[index]) }
    }
    fun retry(){
        //todo
    }
    override fun showErrorScreen() {
        updateState { it.copy(showErrorScreen = true) }
    }

    override fun onClickBack() {
        sendNewEffect(TransferInvoicesUiEffect.NavigateToHomeScreen)
    }
}