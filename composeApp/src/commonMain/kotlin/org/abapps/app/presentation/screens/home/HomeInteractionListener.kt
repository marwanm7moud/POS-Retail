package org.abapps.app.presentation.screens.home

interface HomeInteractionListener {
    fun onClickInvoice()
    fun onClickTransfer()
    fun onUserNameChanged(username: String)
    fun onPasswordChanged(password: String)
    fun onClickExitApp()
    fun onClickSettings()
    fun onClickSettingsOk()
    fun showErrorDialogue()
    fun showWarningDialogue()
    fun onDismissErrorDialogue()
    fun onDismissWarningDialogue()
    fun onDismissSettingsDialogue()
}