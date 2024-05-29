package org.abapps.app.presentation.screens.posInvoiceScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BadgedBox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.StAppBar
import com.beepbeep.designSystem.ui.composable.StButton
import com.beepbeep.designSystem.ui.composable.StDialogue
import com.beepbeep.designSystem.ui.composable.StOutlinedButton
import com.beepbeep.designSystem.ui.composable.StTextField
import com.beepbeep.designSystem.ui.composable.animate.FadeAnimation
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.data.util.RetailSetup
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.presentation.screens.allinvoices.AllInvoicesScreen
import org.abapps.app.presentation.screens.composable.DropDownTextField
import org.abapps.app.presentation.screens.composable.ErrorDialogue
import org.abapps.app.presentation.screens.composable.HandleErrorState
import org.abapps.app.presentation.screens.composable.SetLayoutDirection
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.AllItemTable
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.CalculationsBar
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.ExpandableCard
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.NewInvoiceItemTable
import org.abapps.app.presentation.util.EventHandler
import org.abapps.app.resource.Resources
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
                    navigator.replace(AllInvoicesScreen())
                }
            }
        }

        LaunchedEffect(state.errorState) {
            if (state.errorState != null) invoicesScreenModel.showErrorScreen()
        }

        FadeAnimation(state.errorDialogueIsVisible) {
            ErrorDialogue(
                title = Resources.strings.error,
                text = state.errorMessage,
                onDismissRequest = invoicesScreenModel::onDismissErrorDialogue,
                onClickConfirmButton = invoicesScreenModel::onDismissErrorDialogue,
            )
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                StAppBar(onNavigateUp = {
                    invoicesScreenModel.onClickBack()
                },
                    title = if (!state.isAddItem) Resources.strings.invoice else Resources.strings.allItems,
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
                                    Resources.strings.addItem,
                                    style = Theme.typography.title.copy(color = Color.White)
                                )
                            }
                    })
            },
            bottomBar = {
                AnimatedVisibility(!state.isAddItem && !state.isLoading && !state.showErrorScreen) {
                    CalculationsBar(state, invoicesScreenModel)
                }
            },
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
                            ?: ErrorState.UnknownError(Resources.strings.somethingWrongHappened),
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
            FadeAnimation(visible = state.showDiscountDialog) {
                DiscountDialog(state, invoicesScreenModel)
            }

            AnimatedVisibility(state.isAddItem && !state.isLoading && !state.showErrorScreen) {
                AllItemTable(
                    modifier = Modifier.padding(top = it.calculateTopPadding()),
                    invoiceItems = state.allItemsList,
                    selectedItemIndex = state.selectedItemsIndexFromAllItems,
                    onClickItem = invoicesScreenModel::onClickItemFromAllItems
                )
            }
            AnimatedVisibility(!state.isAddItem && !state.isLoading && !state.showErrorScreen) {
                Column(
                    modifier = Modifier.padding(
                        top = it.calculateTopPadding(),
                        //bottom = it.calculateBottomPadding()
                    ).padding(top = 8.dp).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ExpandableCard(
                        title = Resources.strings.brandon,
                        expandedState = state.expandedCardStatus == ExpandedCardStatus.Brandon,
                        onClickCard = {
                            invoicesScreenModel.onClickExpandedCard(ExpandedCardStatus.Brandon)
                        }
                    ) {
                        BrandonCard(
                            state = state,
                            listener = invoicesScreenModel as InvoiceInteractions
                        )
                    }
                    ExpandableCard(
                        title = Resources.strings.items,
                        expandedState = state.expandedCardStatus == ExpandedCardStatus.Items,
                        onClickCard = {
                            invoicesScreenModel.onClickExpandedCard(ExpandedCardStatus.Items)
                        }
                    ) {
                        NewInvoiceItemTable(
                            modifier = Modifier,
                            invoiceItems = state.invoiceItemList.sortedBy { it.itemCode },
                            selectedItemIndex = state.selectedItemIndexFromInvoice,
                            onClickItem = invoicesScreenModel::onClickItemFromInvoice,
                            onClickItemDiscount = invoicesScreenModel::onClickItemDiscount,
                            onClickItemDelete = invoicesScreenModel::onClickItemDelete,
                            onChangeQty = invoicesScreenModel::onChangeQty
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BrandonCard(
    modifier: Modifier = Modifier,
    state: NewInvoiceUiState,
    listener: InvoiceInteractions
) {
    Column(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                StTextField(
                    label = Resources.strings.invoiceNumber,
                    text = state.invoiceNumber.toString(),
                    onValueChange = {},
                    readOnly = true
                )
            }
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = state.invoiceTypes.map { it.toDropDownState() },
                selectedItem = state.selectedInvoiceType.toDropDownState(),
                label = Resources.strings.invoiceType
            ) { listener.onChooseInvoiceType(it) }
            Box(modifier = Modifier.weight(1f)) {
                StTextField(
                    label = Resources.strings.status,
                    text = state.selectedInvoiceType.name,
                    onValueChange = {},
                    readOnly = true
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                DropDownTextField(
                    options = state.customers.map { it.toDropDownState() },
                    selectedItem = state.selectedCustomer.toDropDownState(),
                    label = Resources.strings.customer
                ) { listener.onChooseCustomer(it) }
            }
            Box(modifier = Modifier.weight(1f)) {
                StTextField(
                    label = Resources.strings.sourceType,
                    text = "Invoice",
                    onValueChange = {},
                    readOnly = true
                )
            }
//            DropDownTextField(
//                modifier = Modifier.weight(1f),
//                options = listOf(
//                    DropDownState(0, "gg"),
//                    DropDownState(1, "gg"),
//                    DropDownState(2, "gg")
//                ),
//                selectedItem = DropDownState(0, "gg"),
//                label = "Sales Order"
//            ) {
//
//            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = state.stores.map { it.toDropDownState() },
                selectedItem = state.selectedStore.toDropDownState(),
                label = Resources.strings.store,
                enabled = RetailSetup.IS_MAIN_STORE
            ) { listener.onChooseStore(it) }
            StTextField(
                modifier = Modifier.weight(1f),
                label = Resources.strings.cashier,
                text = state.cashierName,
                onValueChange = {},
                readOnly = true
            )
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = state.sales.map { it.toDropDownState() },
                selectedItem = state.selectedSalePerson.toDropDownState(),
                label = Resources.strings.salesPerson
            ) { listener.onChooseSalesPerson(it) }
        }
        StTextField(
            label = Resources.strings.comment,
            textFieldModifier = Modifier.fillMaxWidth().height(96.dp),
            text = state.comment,
            onValueChange = listener::onCommentChanged,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DiscountDialog(state: NewInvoiceUiState, listener: InvoiceInteractions) {
    StDialogue(
        onDismissRequest = listener::onDismissSettingsDialogue,
    ) {
        Text(
            text = Resources.strings.enterYourInfo,
            style = Theme.typography.headline,
            color = Theme.colors.contentPrimary,
        )

        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Amount",
                    color = Color.White,
                    modifier = Modifier.width(80.dp)
                )
                Text(
                    text = state.calculations.discountAmount.toString(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(120.dp)
                        .border(
                            BorderStroke(0.5.dp, Color.LightGray),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 8.dp)
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Amount after discount",
                    color = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = ((state.calculations.discountAmount / 100) * state.calculations.subTotal).toString(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(120.dp)
                        .border(
                            BorderStroke(0.5.dp, Color.LightGray),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 8.dp)
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        DropDownTextField(
            modifier = Modifier,
            options = state.discounts.map { it.toDropDownState() },
            selectedItem = state.selectedDiscount.toDropDownState(),
            label = Resources.strings.discount
        ) {
            listener.onChooseDiscount(it)
        }

        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Percentage",
                    color = Color.White,
                    modifier = Modifier.width(80.dp)
                )
                BasicTextField(
                    value = state.calculations.discountAmount.toString(),
                    onValueChange = listener::onChangeDiscount,
                    textStyle = TextStyle(
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    readOnly = state.selectedDiscount.type != "Open_Amount",
                    modifier = Modifier.width(120.dp)
                        .border(
                            BorderStroke(0.5.dp, Color.LightGray),
                            RoundedCornerShape(12.dp)
                        ).padding(vertical = 8.dp)
                )
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Discount Amount",
                    color = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = ((state.calculations.discountAmount / 100) * state.calculations.subTotal).toString(),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(120.dp)
                        .border(
                            BorderStroke(0.5.dp, Color.LightGray),
                            RoundedCornerShape(12.dp)
                        ).padding(vertical = 8.dp)
                )
            }
        }

        SetLayoutDirection(layoutDirection = LayoutDirection.Ltr) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StOutlinedButton(
                    title = Resources.strings.cancel,
                    onClick = listener::onDismissSettingsDialogue,
                    modifier = Modifier.weight(1f)
                )
                StButton(
                    title = Resources.strings.ok,
                    onClick = listener::onClickOkInDiscountDialog,
                    modifier = Modifier.weight(1f),
                    isLoading = false,
                )
            }
        }
    }
}