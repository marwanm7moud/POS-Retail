package org.abapps.app.presentation.screens.settings

interface SettingInteractionListener {
    fun onClickSave()
    fun onClickClose()
    fun onChooseSubCompany(id: Int)
    fun onChooseStore(id: Int)
    fun onChooseMainSubCompany(id: Int)
    fun onChooseMainStore(id: Int)
    fun onApiUrlChanged(apiUrl: String)
    fun onWorkStationIdChanged(wsId: String)
    fun onClickBack()
}