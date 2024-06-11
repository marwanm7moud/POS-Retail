package org.abapps.app.presentation.screens.transferNewInvoice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BadgedBox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.StAppBar
import com.beepbeep.designSystem.ui.composable.StButton
import com.beepbeep.designSystem.ui.composable.StTextField
import com.beepbeep.designSystem.ui.composable.animate.FadeAnimation
import com.beepbeep.designSystem.ui.theme.Theme
import io.ktor.util.date.GMTDate
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.presentation.screens.composable.DropDownTextField
import org.abapps.app.presentation.screens.composable.ErrorDialogue
import org.abapps.app.presentation.screens.composable.HandleErrorState
import org.abapps.app.presentation.screens.composable.SetLayoutDirection
import org.abapps.app.presentation.screens.composable.TimePickerDialog
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.AllItemTable
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.CalculationItem
import org.abapps.app.presentation.screens.posInvoiceScreen.composables.ExpandableCard
import org.abapps.app.presentation.screens.transferInvoices.TransferInvoicesScreen
import org.abapps.app.presentation.screens.transferNewInvoice.composables.TransferInvoiceItemTable
import org.abapps.app.presentation.util.EventHandler
import org.abapps.app.resource.Resources
import org.abapps.app.util.getScreenModel
import org.jetbrains.compose.resources.painterResource
import pos_retail.composeapp.generated.resources.Res
import pos_retail.composeapp.generated.resources.baseline_date_range_24
import pos_retail.composeapp.generated.resources.ic_back

class TransferNewInvoiceScreen : Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<TransferNewInvoiceScreenModel>()
        val state by screenModel.state.collectAsState()



        EventHandler(screenModel.effect) { effect, navigator ->
            when (effect) {
                is TransferNewInvoiceUiEffect.NavigateBackToAllInvoices -> {
                    navigator.push(TransferInvoicesScreen())
                }
            }
        }

        LaunchedEffect(state.errorState) {
            if (state.errorState != null) screenModel.showErrorScreen()
        }


        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                StAppBar(onNavigateUp = {
                    screenModel.onClickBack()
                },
                    title = if (!state.isAddItem) Resources.strings.transferNewInvoice else Resources.strings.allItems,
                    isBackIconVisible = true,
                    painterResource = painterResource(Res.drawable.ic_back),
                    actions = {
                        if (!state.isAddItem)
                            Row(
                                modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable {
                                    screenModel.onClickAddItem()
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
                AnimatedVisibility(!state.isAddItem && !state.isLoading) {
                    var expandedState by remember { mutableStateOf(true) }
                    val rotationState by animateFloatAsState(
                        targetValue = if (expandedState) 180f else 0f
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                                .background(Color(0xFF0202020)).clickable {
                                    expandedState = !expandedState
                                }
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center

                        ) {
                            IconButton(
                                modifier = Modifier
                                    .alpha(0.5f)
                                    .rotate(rotationState),
                                onClick = { expandedState = !expandedState }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Drop-Down Arrow",
                                    tint = Color.White
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .clip(RoundedCornerShape(topStart = 12.dp))
                                .background(Color(0xFF0202020))
                                .padding(16.dp)
                        ) {
                            androidx.compose.animation.AnimatedVisibility(expandedState) {
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    CalculationItem(
                                        Resources.strings.cost,
                                        state.calculations.totalCost.toString()
                                    )
                                    CalculationItem(
                                        Resources.strings.price,
                                        state.calculations.totalPrice.toString()
                                    )
                                    CalculationItem(
                                        Resources.strings.qtyOnHand,
                                        state.calculations.totalQtyOnHand.toString()
                                    )
                                    CalculationItem(
                                        Resources.strings.qtyTransfer,
                                        state.calculations.totalQtyTran.toString()
                                    )
                                }
                            }
                        }
                    }
                }
            },
            floatingActionButton = {
                AnimatedVisibility(state.isAddItem) {
                    Box {
                        FloatingActionButton(
                            onClick = {
                                screenModel.onClickDone()
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
            val items = state.allItemsList.collectAsLazyPagingItems()

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
            FadeAnimation(state.errorDialogueIsVisible) {
                ErrorDialogue(
                    title = Resources.strings.error,
                    text = state.errorMessage,
                    onDismissRequest = screenModel::onDismissErrorDialogue,
                    onClickConfirmButton = screenModel::onDismissErrorDialogue,
                )
            }

            FadeAnimation(visible = state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            AnimatedVisibility(state.isAddItem && !state.isLoading && !state.showErrorScreen) {
                SetLayoutDirection(LayoutDirection.Ltr) {
                    AllItemTable(
                        modifier = Modifier.padding(top = it.calculateTopPadding()),
                        invoiceItems = items,
                        selectedItemIndex = state.selectedItemsIndexFromAllItems,
                        onClickItem = screenModel::onClickItemFromAllItems
                    )
                }

            }
            AnimatedVisibility(!state.isAddItem && !state.isLoading) {
                Column(
                    modifier = Modifier.padding(it).padding(top = 8.dp).padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ExpandableCard(
                        title = Resources.strings.brandon,
                        expandedState = state.expandedCardStatus == ExpandedCardStatus.Brandon,
                        onClickCard = {
                            screenModel.onClickExpandedCard(ExpandedCardStatus.Brandon)
                        }
                    ) {
                        BrandonCard(
                            state = state,
                            listener = screenModel
                        )
                    }
                    ExpandableCard(
                        title = Resources.strings.items,
                        expandedState = state.expandedCardStatus == ExpandedCardStatus.Items,
                        onClickCard = {
                            screenModel.onClickExpandedCard(ExpandedCardStatus.Items)
                        }
                    ) {
                        TransferInvoiceItemTable(
                            modifier = Modifier,
                            invoiceItems = state.invoiceItemList,
                            selectedItemIndex = state.selectedItemIndexFromInvoice,
                            onClickItem = screenModel::onClickItemFromInvoice,
                            onClickItemDelete = screenModel::onClickItemDelete,
                            onChangeQty = screenModel::onChangeQty,
                            onChangeComment = screenModel::onChangeCommentInItem
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BrandonCard(
    modifier: Modifier = Modifier,
    state: TransferNewInvoiceUiState,
    listener: TransferNewInvoiceInteractions
) {

    var visibleDateTime by remember { mutableStateOf(false) }
    val transDateState = rememberDatePickerState()

    val timePickerState = rememberTimePickerState()
    var showTimePicker by remember { mutableStateOf(false) }

    AnimatedVisibility(visibleDateTime) {
        DatePickerDialog(onDismissRequest = {
            visibleDateTime = false
            showTimePicker = false
        }, {
            StButton("Done", onClick = {
                visibleDateTime = false
                showTimePicker = true
            })
        }
        ) {
            DatePicker(
                transDateState, colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = Theme.colors.primary,
                    todayDateBorderColor = Theme.colors.primary
                )
            )
        }
    }
    AnimatedVisibility(showTimePicker) {
        TimePickerDialog(
            onDismissRequest = {
                visibleDateTime = false
                showTimePicker = false
            },
            confirmButton = {
                StButton("Done", onClick = {
                    visibleDateTime = false
                    showTimePicker = false
                })
            }
        ) {
            TimePicker(
                timePickerState, colors = TimePickerDefaults.colors(

                )
            )
        }
    }
    Column(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                StTextField(
                    label = Resources.strings.transferNumber,
                    text = state.transferNumber,
                    onValueChange = listener::onChangeTransferNumber,
                )
            }
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = state.fromStoreOptions.map { it.toDropDownState() },
                selectedItem = state.selectedFromStore.toDropDownState(),
                label = Resources.strings.fromStore
            ) { listener.onChooseFromStore(it) }
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = state.toStoreOptions.map { it.toDropDownState() },
                selectedItem = state.selectedToStore.toDropDownState(),
                label = Resources.strings.toStore
            ) { listener.onChooseToStore(it) }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                StTextField(
                    label = Resources.strings.transType,
                    text = state.transType,
                    onValueChange = listener::onChangeTranstype,
                    //readOnly = true
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                StTextField(
                    modifier = Modifier,
                    label = Resources.strings.transDate,
                    text = dateTimeText(transDateState, timePickerState),
                    onValueChange = {},
                    trailingIcon = {
                        IconButton(onClick = { visibleDateTime = true }) {
                            Icon(
                                painter = painterResource(Res.drawable.baseline_date_range_24),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    },
                    readOnly = true
                )
            }
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = state.statusOptions.map { it.toDropDownState() },
                selectedItem = state.selectedStatus.toDropDownState(),
                label = Resources.strings.status
            ) { listener.onChooseStatus(it) }
        }
        StTextField(
            label = Resources.strings.comment,
            textFieldModifier = Modifier.fillMaxWidth().height(96.dp),
            text = state.comment,
            onValueChange = listener::onChangeComment,
            //readOnly = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun dateTimeText(
    transDateState: DatePickerState,
    timePickerState: TimePickerState
): String {
    val date = transDateState.selectedDateMillis?.let { millis ->
        val date = GMTDate(millis)
        "${date.year}/${date.month.ordinal + 1}/${date.dayOfMonth}"
    } ?: ""

    val time = " ${timePickerState.hour}:${timePickerState.minute}"

    return date + time
}