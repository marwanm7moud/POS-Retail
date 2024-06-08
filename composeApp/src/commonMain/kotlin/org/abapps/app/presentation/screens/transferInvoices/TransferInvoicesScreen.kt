package org.abapps.app.presentation.screens.transferInvoices

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.StAppBar
import com.beepbeep.designSystem.ui.composable.animate.FadeAnimation
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.presentation.screens.composable.HandleErrorState
import org.abapps.app.presentation.screens.home.HomeScreen
import org.abapps.app.presentation.screens.transferInvoices.composables.TransferInvoicesTable
import org.abapps.app.presentation.screens.transferNewInvoice.TransferNewInvoiceScreen
import org.abapps.app.presentation.util.EventHandler
import org.abapps.app.resource.Resources
import org.abapps.app.util.getScreenModel
import org.jetbrains.compose.resources.painterResource
import pos_retail.composeapp.generated.resources.Res
import pos_retail.composeapp.generated.resources.ic_back

class TransferInvoicesScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<TransferInvoicesScreenModel>()
        val state by screenModel.state.collectAsState()

        EventHandler(screenModel.effect){effect, navigator ->
            when(effect){
                is TransferInvoicesUiEffect.NavigateToNewInvoice-> {
                    navigator.push(TransferNewInvoiceScreen())
                }
                is TransferInvoicesUiEffect.NavigateToHomeScreen-> {
                    navigator.replace(HomeScreen())
                }
            }
        }
        LaunchedEffect(state.errorState) {
            if (state.errorState != null) screenModel.showErrorScreen()
        }

        Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Transparent,
            topBar = {
                StAppBar(
                    onNavigateUp = { screenModel.onClickBack() },
                    title = Resources.strings.transferTitle,
                    isBackIconVisible = true,
                    painterResource = painterResource(Res.drawable.ic_back),
                )
            },
            floatingActionButton = {
                Box {
                    FloatingActionButton(
                        onClick = screenModel::onClickFloatActionButton,
                        containerColor = Theme.colors.primary
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = null,
                                tint = Theme.colors.contentPrimary
                            )
                            Text(
                                Resources.strings.newInvoice,
                                style = Theme.typography.title.copy(color = Color.White)
                            )
                        }
                    }
                }
            })
        {

            FadeAnimation(state.showErrorScreen) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    HandleErrorState(
                        title = state.errorMessage,
                        error = state.errorState
                            ?: ErrorState.UnknownError("Something wrong happened please try again later!"),
                        onClick = screenModel::retry
                    )
                }
            }

            FadeAnimation(visible = state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            AnimatedVisibility(!state.isLoading){
                TransferInvoicesTable(
                    modifier = Modifier.padding(top = it.calculateTopPadding()),
                    invoiceItems = state.invoicesList,
                    selectedItemIndex = state.selectedInvoiceIndex,
                    onClickItem = screenModel::onClickItem,
                    onClickItemEdit = {},
                    onClickItemDelete = screenModel::onClickItemDelete,
                    onClickItemCopy = {}
                )
            }
        }
    }

}