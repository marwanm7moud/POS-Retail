package org.abapps.app.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.beepbeep.designSystem.ui.composable.StButton
import com.beepbeep.designSystem.ui.composable.StDialogue
import com.beepbeep.designSystem.ui.composable.StOutlinedButton
import com.beepbeep.designSystem.ui.composable.StTextField
import com.beepbeep.designSystem.ui.composable.animate.FadeAnimation
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.exitApplication
import org.abapps.app.kms
import org.abapps.app.presentation.screens.allinvoices.AllInvoicesScreen
import org.abapps.app.presentation.screens.composable.AppScaffold
import org.abapps.app.presentation.screens.composable.ErrorDialogue
import org.abapps.app.presentation.screens.composable.SetLayoutDirection
import org.abapps.app.presentation.screens.composable.WarningDialogue
import org.abapps.app.presentation.screens.composable.modifier.bounceClick
import org.abapps.app.presentation.screens.composable.snackbar.rememberStackedSnackbarHostState
import org.abapps.app.presentation.screens.transferInvoices.TransferInvoicesScreen
import org.abapps.app.presentation.util.EventHandler
import org.abapps.app.resource.Resources
import org.jetbrains.compose.resources.painterResource
import pos_retail.composeapp.generated.resources.Res
import pos_retail.composeapp.generated.resources.admin
import pos_retail.composeapp.generated.resources.bank_transfer
import pos_retail.composeapp.generated.resources.exit
import pos_retail.composeapp.generated.resources.invoice
import org.abapps.app.presentation.screens.composable.IconWithBackground as IconWithBackground1

class HomeScreen : Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val homeScreenModel = getScreenModel<HomeScreenModel>()
        val state by homeScreenModel.state.collectAsState()
        val retry = Resources.strings.retry

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

        FadeAnimation(visible = state.settingsDialogueState.isVisible) {
            SettingsDialogue(
                username = state.settingsDialogueState.username,
                password = state.settingsDialogueState.password,
                isLoading = state.settingsDialogueState.isLoading,
                homeInteractionListener = homeScreenModel as HomeInteractionListener
            )
        }

        val stackedSnackbarHostState = rememberStackedSnackbarHostState(maxStack = 1)

        AppScaffold(
            isLoading = state.isLoading,
            stackedSnackbarHostState = stackedSnackbarHostState,
            error = state.errorHomeDetailsState,
            onError = {
                LaunchedEffect(state.errorHomeDetailsState) {
                    if (state.errorHomeDetailsState != null && state.errorMessage.isNotEmpty())
                        stackedSnackbarHostState.showErrorSnackbar(
                            title = "Something happened, try again!",
                            description = state.errorMessage,
                            actionTitle = retry,
                        ) {
                            homeScreenModel.retry()
                        }
                }
            }
        ) {
            LaunchedEffect(state.errorState) {
                if (state.errorState != null && state.errorMessage.isNotEmpty())
                    homeScreenModel.showErrorDialogue()
            }

            FadeAnimation(state.errorDialogueIsVisible) {
                ErrorDialogue(
                    title = Resources.strings.error,
                    text = state.errorMessage,
                    onDismissRequest = homeScreenModel::onDismissErrorDialogue,
                    onClickConfirmButton = homeScreenModel::onDismissErrorDialogue,
                )
            }

            SetLayoutDirection(layoutDirection = LayoutDirection.Ltr) {
                FlowRow(
                    modifier = Modifier.fillMaxSize(),
                    maxItemsInEachRow = 2,
                ) {
                    IconWithBackground1(
                        icon = painterResource(Res.drawable.invoice),
                        contentDescription = Resources.strings.invoice,
                        modifier = Modifier.size(80.kms)
                            .bounceClick { homeScreenModel.onClickInvoice() },
                        iconSize = 65.kms,
                    )
                    IconWithBackground1(
                        icon = painterResource(Res.drawable.bank_transfer),
                        contentDescription = Resources.strings.transfer,
                        modifier = Modifier.size(80.kms)
                            .bounceClick { homeScreenModel.onClickTransfer() },
                        iconSize = 65.kms,
                    )
                    IconWithBackground1(
                        icon = painterResource(Res.drawable.admin),
                        contentDescription = Resources.strings.settings,
                        modifier = Modifier.size(80.kms)
                            .bounceClick { homeScreenModel.onClickSettings() },
                        iconSize = 65.kms,
                    )
                    IconWithBackground1(
                        icon = painterResource(Res.drawable.exit),
                        contentDescription = Resources.strings.exit,
                        modifier = Modifier.size(80.kms)
                            .bounceClick { homeScreenModel.onClickExitApp() },
                        iconSize = 65.kms,
                    )
                }
            }
        }

//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//
//            Column {
//                Button(
//                    modifier = Modifier.fillMaxWidth(0.5f).height(56.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Theme.colors.primary),
//                    shape = RoundedCornerShape(12.dp),
//                    onClick = { }
//                ) {
//                    Text("POS")
//                }
//                Spacer(modifier = Modifier.height(16.dp))
//                Button(
//                    modifier = Modifier.fillMaxWidth(0.5f).height(56.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Theme.colors.primary),
//                    shape = RoundedCornerShape(12.dp),
//                    onClick = { }
//                ) {
//                    Text("Transfer")
//                }
//            }
//        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsDialogue(
    username: String,
    password: String,
    isLoading: Boolean,
    homeInteractionListener: HomeInteractionListener,
    modifier: Modifier = Modifier,
) {
    StDialogue(
        onDismissRequest = homeInteractionListener::onDismissSettingsDialogue,
        modifier = modifier
    ) {
        Text(
            text = Resources.strings.enterYourInfo,
            style = Theme.typography.headline,
            color = Theme.colors.contentPrimary,
        )
        StTextField(
            modifier = Modifier.padding(top = 40.dp),
            label = Resources.strings.userName,
            text = username,
            hint = Resources.strings.userName,
            onValueChange = homeInteractionListener::onUserNameChanged,
        )
        StTextField(
            modifier = Modifier.padding(top = 24.dp),
            label = Resources.strings.password,
            text = password,
            hint = Resources.strings.password,
            onValueChange = homeInteractionListener::onPasswordChanged,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Go,
            keyboardActions = KeyboardActions(onGo = {
                homeInteractionListener.onClickSettingsOk()
            })
        )
        SetLayoutDirection(layoutDirection = LayoutDirection.Ltr) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StOutlinedButton(
                    title = Resources.strings.cancel,
                    onClick = {
                        homeInteractionListener.onDismissSettingsDialogue()
                    },
                    modifier = Modifier.weight(1f)
                )
                StButton(
                    title = Resources.strings.ok,
                    onClick = {
                        homeInteractionListener.onClickSettingsOk()
                    },
                    modifier = Modifier.weight(1f),
                    isLoading = isLoading,
                )
            }
        }
    }
}