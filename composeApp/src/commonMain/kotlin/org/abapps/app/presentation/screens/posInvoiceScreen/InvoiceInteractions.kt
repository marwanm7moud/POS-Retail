package org.abapps.app.presentation.screens.posInvoiceScreen

interface InvoiceInteractions {
    fun onClickAddItem()
    fun onClickDone()
    fun onClickBack()
    fun onClickItemFromAllItems(index: Int)
    fun onClickItemFromInvoice(index: Int)
    fun onClickExpandedCard(expandedCardStatus: ExpandedCardStatus)
    fun onClickItemDelete(index: Int)
    fun showErrorScreen()
    fun onChooseCustomer(id: Int)
    fun onChooseStore(id: Int)
    fun onChooseSalesPerson(id: Int)
    fun onChooseInvoiceType(id: Int)
    fun onCommentChanged(comment: String)
}