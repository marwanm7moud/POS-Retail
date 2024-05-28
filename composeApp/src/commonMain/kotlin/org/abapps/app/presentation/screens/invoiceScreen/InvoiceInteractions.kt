package org.abapps.app.presentation.screens.invoiceScreen

interface InvoiceInteractions {
    fun onClickAddItem()
    fun onClickDone()
    fun onClickBack()
    fun onClickItemFromAllItems(index:Int)
    fun onClickItemFromInvoice(index:Int)
    fun onClickExpandedCard(expandedCardStatus:ExpandedCardStatus)
    fun onClickItemDelete(index: Int)
    fun showErrorScreen()

}