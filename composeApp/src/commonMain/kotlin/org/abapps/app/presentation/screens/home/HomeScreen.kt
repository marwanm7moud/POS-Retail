package org.abapps.app.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.beepbeep.designSystem.ui.composable.animate.FadeAnimation
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.exitApplication
import org.abapps.app.presentation.screens.allinvoices.AllInvoicesScreen
import org.abapps.app.presentation.screens.composable.WarningDialogue
import org.abapps.app.presentation.screens.transferInvoices.TransferInvoicesScreen
import org.abapps.app.presentation.util.EventHandler
import org.abapps.app.resource.Resources

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val homeScreenModel = getScreenModel<HomeScreenModel>()
        val state by homeScreenModel.state.collectAsState()

        FadeAnimation(state.warningDialogueIsVisible) {
            WarningDialogue(
                Resources.strings.warning,
                Resources.strings.doYouWantToCloseApp,
                onDismissRequest = homeScreenModel::onDismissWarningDialogue,
                onClickConfirmButton = { homeScreenModel.exitApp() },
                onClickDismissButton = homeScreenModel::onDismissWarningDialogue
            )
        }
        if (state.exitApp) exitApplication()

        EventHandler(homeScreenModel.effect) { effect, navigator ->
            when (effect) {
                is HomeUiEffect.NavigateToInvoiceScreen -> navigator.push(AllInvoicesScreen())
                HomeUiEffect.NavigateToSettingScreen -> {}
                HomeUiEffect.NavigateToTransferScreen -> navigator.push(TransferInvoicesScreen())
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Button(
                    modifier = Modifier.fillMaxWidth(0.5f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Theme.colors.primary),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { }
                ) {
                    Text("POS")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(0.5f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Theme.colors.primary),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { }
                ) {
                    Text("Transfer")
                }
            }
        }
    }
}