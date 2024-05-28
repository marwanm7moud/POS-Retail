package org.abapps.app.presentation.screens.allinvoices

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import org.abapps.app.presentation.base.BaseScreenModel

class AllInvoicesScreenModel() :
    BaseScreenModel<AllInvoicesState, AllInvoicesUiEffect>(AllInvoicesState()),
    AllInvoicesInteraction {


    override val viewModelScope: CoroutineScope get() = screenModelScope

    fun onClickFloatActionButton() {
        sendNewEffect(AllInvoicesUiEffect.NavigateToNewInvoice)
    }

    override fun onClickItem(index: Int) {
        updateState { it.copy(selectedInvoiceIndex = index) }
    }
    override fun onClickItemDelete(index: Int) {
        updateState { it.copy(invoicesList = it.invoicesList - it.invoicesList[index]) }
    }
}