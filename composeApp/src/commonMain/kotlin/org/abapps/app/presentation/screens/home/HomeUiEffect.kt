package org.abapps.app.presentation.screens.home

sealed interface HomeUiEffect {
    data object NavigateToInvoiceScreen : HomeUiEffect
    data object NavigateToTransferScreen : HomeUiEffect
    data object NavigateToSettingScreen : HomeUiEffect
}