package org.abapps.app.presentation.screens.home

import androidx.compose.runtime.Immutable
import org.abapps.app.presentation.base.ErrorState

@Immutable
data class HomeState(
    val errorMessage: String = "",
    val errorHomeDetailsState: ErrorState? = null,
    val errorState: ErrorState? = null,
    val isLoading: Boolean = false,
    val settingsDialogueState: SettingsDialogueState = SettingsDialogueState(),
    val errorDialogueIsVisible: Boolean = false,
    val warningDialogueIsVisible: Boolean = false,
    val exitApp: Boolean = false,
)

@Immutable
data class SettingsDialogueState(
    val isLoading: Boolean = false,
    val isVisible: Boolean = false,
    val username: String = "",
    val password: String = "",
)