package org.abapps.app.presentation.screens.settings

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import org.abapps.app.presentation.base.BaseScreenModel
import org.abapps.app.presentation.base.ErrorState

class SettingsScreenModel : BaseScreenModel<SettingState, SettingUiEffect>(SettingState()),
    SettingInteractionListener {
    override val viewModelScope: CoroutineScope get() = screenModelScope

    override fun onClickSave() {
        updateState { it.copy(isLoading = true, isSuccess = false) }

    }

    fun retry() {

    }

    override fun onClickClose() {
        updateState { it.copy(isSuccess = false) }
        sendNewEffect(SettingUiEffect.NavigateBackToHome)
    }

    override fun onChooseSubCompany(id: Int) {
        updateState { it.copy(selectedSubCompany = it.selectedSubCompany.copy(id = id)) }
    }

    override fun onChooseStore(id: Int) {
        updateState { it.copy(selectedStore = it.selectedStore.copy(id = id)) }
    }

    override fun onChooseMainSubCompany(id: Int) {
        updateState { it.copy(selectedMainSubCompany = it.selectedMainSubCompany.copy(id = id)) }
    }

    override fun onChooseMainStore(id: Int) {
        updateState { it.copy(selectedMainStore = it.selectedMainStore.copy(id = id)) }
    }

    override fun onApiUrlChanged(apiUrl: String) {
        updateState { it.copy(apiUrl = apiUrl) }
    }

    override fun onWorkStationIdChanged(wsId: String) {
        updateState { it.copy(workStationId = wsId) }
    }

    override fun onClickBack() {
        sendNewEffect(SettingUiEffect.NavigateBackToHome)
    }


    private fun onError(error: ErrorState) {
        updateState {
            it.copy(
                errorState = error,
                isLoading = false,
                errorMessage = when (error) {
                    is ErrorState.UnknownError -> error.message.toString()
                    is ErrorState.ServerError -> error.message.toString()
                    is ErrorState.NotFound -> error.message.toString()
                    is ErrorState.ValidationNetworkError -> error.message.toString()
                    is ErrorState.NetworkError -> error.message.toString()
                    else -> "Something wrong happened please try again !"
                }
            )
        }
    }
}