package org.abapps.app.presentation.screens.home

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import org.abapps.app.data.util.AppLanguage
import org.abapps.app.domain.usecase.AdminSystemUseCase
import org.abapps.app.presentation.base.BaseScreenModel
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.resource.strings.IStringResources
import org.abapps.app.util.LanguageCode
import org.abapps.app.util.LocalizationManager

class HomeScreenModel(
    private val adminSystem: AdminSystemUseCase
) : BaseScreenModel<HomeState, HomeUiEffect>(HomeState()),
    HomeInteractionListener {
    override val viewModelScope: CoroutineScope get() = screenModelScope
    private var localMessage: IStringResources = LocalizationManager.getStringResources(
        LanguageCode.entries.find { code ->
            code.value == AppLanguage.code.value
        } ?: LanguageCode.EN
    )

    override fun onClickInvoice() {
        sendNewEffect(HomeUiEffect.NavigateToInvoiceScreen)
    }

    override fun onClickTransfer() {
        sendNewEffect(HomeUiEffect.NavigateToTransferScreen)
    }

    fun retry() {

    }

    override fun onUserNameChanged(username: String) {
        updateState {
            it.copy(
                settingsDialogueState = it.settingsDialogueState.copy(
                    username = username
                )
            )
        }
    }

    override fun onPasswordChanged(password: String) {
        updateState {
            it.copy(
                settingsDialogueState = it.settingsDialogueState.copy(
                    password = password
                )
            )
        }
    }

    override fun onClickExitApp() {
        showWarningDialogue()
    }

    fun exitApp() {
        updateState {
            it.copy(
                errorMessage = "",
                isLoading = false,
                errorState = null,
                exitApp = true,
                warningDialogueIsVisible = false
            )
        }
    }

    override fun onClickSettings() {
        updateState {
            it.copy(
                settingsDialogueState = SettingsDialogueState(
                    isVisible = true
                )
            )
        }
    }

    override fun onClickSettingsOk() {
        updateState {
            it.copy(
                errorState = null,
                errorMessage = "",
                settingsDialogueState = it.settingsDialogueState.copy(isLoading = true)
            )
        }
        if (adminSystem.checkPermissionWithPassword(state.value.settingsDialogueState.password)) {
            updateState {
                it.copy(
                    settingsDialogueState = it.settingsDialogueState.copy(
                        isLoading = false,
                        isVisible = false
                    )
                )
            }
            launchDelayed(1000) {
                updateState {
                    it.copy(
                        settingsDialogueState = SettingsDialogueState()
                    )
                }
            }
            sendNewEffect(HomeUiEffect.NavigateToSettingScreen)
        } else updateState {
            it.copy(
                errorMessage = localMessage.logonError,
                errorState = ErrorState.PermissionDenied(localMessage.logonError),
                settingsDialogueState = it.settingsDialogueState.copy(isLoading = false),
            )
        }
    }

    override fun showErrorDialogue() {
        updateState { it.copy(errorDialogueIsVisible = true, warningDialogueIsVisible = false) }
    }

    override fun showWarningDialogue() {
        updateState { it.copy(warningDialogueIsVisible = true) }
    }

    override fun onDismissErrorDialogue() {
        updateState {
            it.copy(
                errorDialogueIsVisible = false,
                warningDialogueIsVisible = false,
                errorState = null,
            )
        }
    }

    override fun onDismissWarningDialogue() {
        updateState {
            it.copy(
                warningDialogueIsVisible = false,
                errorState = null,
            )
        }
    }

    override fun onDismissSettingsDialogue() {
        updateState {
            it.copy(
                errorMessage = "",
                isLoading = false,
                errorState = null,
                settingsDialogueState = it.settingsDialogueState.copy(
                    isVisible = false,
                    isLoading = false
                )
            )
        }
    }

    private fun onFailed(errorState: ErrorState) {
        updateState {
            it.copy(
                errorState = errorState,
                errorMessage = when (errorState) {
                    is ErrorState.UnknownError -> errorState.message.toString()
                    is ErrorState.ServerError -> errorState.message.toString()
                    is ErrorState.UnAuthorized -> errorState.message.toString()
                    is ErrorState.NotFound -> errorState.message.toString()
                    is ErrorState.ValidationNetworkError -> errorState.message.toString()
                    is ErrorState.NetworkError -> errorState.message.toString()
                    is ErrorState.ValidationError -> errorState.message.toString()
                    else -> "Something wrong happened please try again !"
                }
            )
        }
    }
}