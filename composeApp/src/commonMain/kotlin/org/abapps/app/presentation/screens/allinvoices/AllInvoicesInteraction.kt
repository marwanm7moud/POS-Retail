package org.abapps.app.presentation.screens.allinvoices

interface AllInvoicesInteraction {
    fun onClickItem(index:Int)
    fun showErrorScreen()
    fun onClickBack()

}