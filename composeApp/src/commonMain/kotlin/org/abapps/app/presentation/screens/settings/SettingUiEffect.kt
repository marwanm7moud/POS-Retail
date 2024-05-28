package org.abapps.app.presentation.screens.settings

sealed interface SettingUiEffect {
    data object NavigateBackToHome : SettingUiEffect
}