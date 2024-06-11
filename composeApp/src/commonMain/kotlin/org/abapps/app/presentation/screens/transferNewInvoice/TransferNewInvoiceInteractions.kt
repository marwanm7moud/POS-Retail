package org.abapps.app.presentation.screens.transferNewInvoice

interface TransferNewInvoiceInteractions {
    fun onChangeQty(text:String , itemID:Long)
    fun onChangeCommentInItem(text:String, itemID:Long)

    fun onClickAddItem()
    fun onClickDone()
    fun onClickBack()
    fun onClickItemFromAllItems(index:Int)
    fun onClickItemFromInvoice(index:Int)
    fun onClickExpandedCard(expandedCardStatus:ExpandedCardStatus)
    fun onClickItemDelete(index: Int)
    fun showErrorScreen()
    fun onDismissErrorDialogue()
    fun onChangeTransferNumber(text: String)
    fun onChangeComment(text: String)
    fun onChangeTranstype(text: String)
    fun onChooseFromStore(index: Long)
    fun onChooseToStore(index: Long)
    fun onChooseStatus(index: Long)

}