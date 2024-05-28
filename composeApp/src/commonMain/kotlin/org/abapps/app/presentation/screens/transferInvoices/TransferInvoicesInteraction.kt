package org.abapps.app.presentation.screens.transferInvoices

interface TransferInvoicesInteraction {
    fun onClickItem(index:Int)
    fun onClickItemDelete(index: Int)
    fun showErrorScreen()
    fun onClickBack()

}