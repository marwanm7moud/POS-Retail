package org.abapps.app.presentation.screens.transferNewInvoice

interface TransferNewInvoiceInteractions {
    fun onChangeQty(text:String , itemID:Long)
    fun onChangeComment(text:String , itemID:Long)

    fun onClickAddItem()
    fun onClickDone()
    fun onClickBack()
    fun onClickItemFromAllItems(index:Int)
    fun onClickItemFromInvoice(index:Int)
    fun onClickExpandedCard(expandedCardStatus:ExpandedCardStatus)
    fun onClickItemDelete(index: Int)
    fun showErrorScreen()
    fun onDismissErrorDialogue()

}