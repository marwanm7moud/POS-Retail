package org.abapps.app.presentation.screens.settings

import androidx.compose.runtime.Immutable
import org.abapps.app.domain.entities.StoreSetting
import org.abapps.app.domain.entities.SubCompanySetting
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.presentation.screens.composable.DropDownState

@Immutable
data class SettingState(
    val errorMessage: String = "",
    val errorState: ErrorState? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val stores: List<SetupItemState> = emptyList(),
    val selectedStore: SetupItemState = SetupItemState(),
    val selectedSubCompany: SetupItemState = SetupItemState(),
    val selectedMainSubCompany: SetupItemState = SetupItemState(),
    val selectedMainStore: SetupItemState = SetupItemState(),
    val subCompanies: List<SetupItemState> = emptyList(),
    val apiUrl: String = "",
    val workStationId: String = "0",
)

@Immutable
data class SetupItemState(
    val id: Int = 0,
    val name: String = ""
)

fun SubCompanySetting.toSetupItemState(): SetupItemState = SetupItemState(
    id, name
)

fun StoreSetting.toSetupItemState(): SetupItemState = SetupItemState(
    id, name
)

fun SetupItemState.toDropDownState(): DropDownState = DropDownState(
    id, name
)