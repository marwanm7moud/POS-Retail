package org.abapps.app.presentation.screens.posInvoiceScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BadgedBox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.StAppBar
import com.beepbeep.designSystem.ui.composable.animate.FadeAnimation
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.presentation.screens.allinvoices.AllInvoicesScreen
import org.abapps.app.presentation.screens.composable.HandleErrorState
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.AllItemTable
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.BrandonCard
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.CalculationsBar
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.ExpandableCard
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.NewInvoiceItemTable
import org.abapps.app.presentation.util.EventHandler
import org.abapps.app.util.getScreenModel
import org.jetbrains.compose.resources.painterResource
import pos_retail.composeapp.generated.resources.Res
import pos_retail.composeapp.generated.resources.ic_back

class InvoiceScreen : Screen {
    @Composable
    override fun Content() {
        val invoicesScreenModel = getScreenModel<InvoiceScreenModel>()
        val state by invoicesScreenModel.state.collectAsState()



        EventHandler(invoicesScreenModel.effect) { effect, navigator ->
            when (effect) {
                is InvoiceUiEffect.NavigateBackToAllInvoices -> {
                    navigator.push(AllInvoicesScreen())
                }
            }
        }

        LaunchedEffect(state.errorState) {
            if (state.errorState != null) invoicesScreenModel.showErrorScreen()
        }


        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                StAppBar(onNavigateUp = {
                    invoicesScreenModel.onClickBack()
                },
                    title = if (!state.isAddItem) "Invoice" else "All Items",
                    isBackIconVisible = true,
                    painterResource = painterResource(Res.drawable.ic_back),
                    actions = {
                        if (!state.isAddItem)
                            Row(
                                modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable {
                                    invoicesScreenModel.onClickAddItem()
                                }.padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = null,
                                    tint = Theme.colors.contentPrimary
                                )
                                Text(
                                    "Add Item",
                                    style = Theme.typography.title.copy(color = Color.White)
                                )
                            }
                    })
            },
            bottomBar = { AnimatedVisibility(!state.isAddItem && !state.isLoading) { CalculationsBar() } },
            floatingActionButton = {
                AnimatedVisibility(state.isAddItem) {
                    Box {
                        FloatingActionButton(
                            onClick = {
                                invoicesScreenModel.onClickDone()
                            },
                            containerColor = Theme.colors.primary
                        ) {
                            Icon(
                                Icons.Filled.Done,
                                contentDescription = null,
                                tint = Theme.colors.contentPrimary
                            )
                        }
                        BadgedBox(
                            {
                                Box(
                                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(
                                                color = Theme.colors.contentPrimary,
                                                shape = CircleShape
                                            ).align(Alignment.Center).padding(8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            state.selectedItemsIndexFromAllItems.size.toString(),
                                            style = Theme.typography.title,
                                            modifier = Modifier.fillMaxSize()
                                                .align(Alignment.Center),
                                            textAlign = TextAlign.Center,
                                            color = Theme.colors.surface
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.align(Alignment.TopCenter).padding(start = 8.dp)
                        ) {
                        }
                    }
                }
            }
        ) {

            FadeAnimation(state.showErrorScreen) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    HandleErrorState(
                        title = state.errorMessage,
                        error = state.errorState
                            ?: ErrorState.UnknownError("Something wrong happened please try again later!"),
                        onClick = invoicesScreenModel::retry
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

            AnimatedVisibility(state.isAddItem && !state.isLoading) {
                AllItemTable(
                    modifier = Modifier.padding(top = it.calculateTopPadding()),
                    invoiceItems = state.allItemsList,
                    selectedItemIndex = state.selectedItemsIndexFromAllItems,
                    onClickItem = invoicesScreenModel::onClickItemFromAllItems
                )
            }
            AnimatedVisibility(!state.isAddItem && !state.isLoading) {
                Column(
                    modifier = Modifier.padding(it).padding(top = 8.dp).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ExpandableCard(
                        title = "Brandon",
                        expandedState = state.expandedCardStatus == ExpandedCardStatus.Brandon,
                        onClickCard = {
                            invoicesScreenModel.onClickExpandedCard(ExpandedCardStatus.Brandon)
                        }
                    ) {
                        BrandonCard()
                    }
                    ExpandableCard(
                        title = "Items",
                        expandedState = state.expandedCardStatus == ExpandedCardStatus.Items,
                        onClickCard = {
                            invoicesScreenModel.onClickExpandedCard(ExpandedCardStatus.Items)
                        }
                    ) {
                        NewInvoiceItemTable(
                            modifier = Modifier,
                            invoiceItems = state.invoiceItemList,
                            selectedItemIndex = state.selectedItemIndexFromInvoice,
                            onClickItem = invoicesScreenModel::onClickItemFromInvoice,
                            onClickItemDiscount = {},
                            onClickItemDelete = invoicesScreenModel::onClickItemDelete,
                            onClickItemEdit = {},
                        )
                    }
                }
            }
        }
    }
}

