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
    fun onChooseCustomer(id: Long)
    fun onChooseStore(id: Long)
    fun onChooseSalesPerson(id: Long)
    fun onChooseInvoiceType(id: Long)
    fun onCommentChanged(comment: String)
}