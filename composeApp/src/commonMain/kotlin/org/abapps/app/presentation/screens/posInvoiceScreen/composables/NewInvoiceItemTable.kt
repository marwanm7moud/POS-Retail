package org.abapps.app.presentation.screens.posInvoiceScreen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.abapps.app.presentation.screens.composable.itemBox
import org.abapps.app.presentation.screens.posInvoiceScreen.NewInvoiceItemUiState
import org.abapps.app.resource.Resources
import org.abapps.app.util.calculateBiggestWidthOnEveryRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewInvoiceItemTable(
    invoiceItems: List<NewInvoiceItemUiState>,
    selectedItemIndex: Int,
    onClickItem: (Int) -> Unit,
    onClickItemDelete: (Long) -> Unit,
    onClickItemDiscount: (Long) -> Unit,
    modifier: Modifier,
    onChangeQty: (String, Long) -> Unit
) {
    val headers = NewInvoiceItemHeaders.entries.toTypedArray()
    val biggestColumnWidths = calculateBiggestWidths(invoiceItems)

    Column(
        modifier = modifier.padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(12.dp))
            .horizontalScroll(rememberScrollState(0))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.background(color = Color.LightGray).padding(vertical = 8.dp),
        ) {
            headers.forEach { header ->
                itemBox(
                    content = getTitleByHeader(header),
                    modifier = Modifier
                        .padding(4.dp)
                        .width(with(LocalDensity.current) {
                            (biggestColumnWidths[header] ?: 0).toDp()
                        })
                )
            }
        }
        repeat(invoiceItems.size) { index ->
            val item = invoiceItems[index]
            var expanded by remember { mutableStateOf(false) }
            Box {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onClickItemDelete(item.itemID)
                        },
                        text = {
                            Text(text = Resources.strings.delete)
                        },
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onClickItemDiscount(item.itemID)
                        },
                        text = {
                            Text(text = Resources.strings.discount)
                        },
                    )

                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.background(
                        color = if (selectedItemIndex == index) Color(
                            0xFF383a3d
                        ) else Color.Gray
                    ).combinedClickable(
                        onClick = {
                            onClickItem(index)
                        },
                        onLongClick = {
                            onClickItem(index)
                            expanded = !expanded
                        }
                    )
                        .padding(vertical = 4.dp)
                ) {
                    headers.forEach { header ->
                        val content = when (header) {
                            NewInvoiceItemHeaders.ItemCode -> item.itemCode.toString()
                            NewInvoiceItemHeaders.Alu -> item.alu.toString()
                            NewInvoiceItemHeaders.Name -> item.name
                            NewInvoiceItemHeaders.Qty -> item.qty.toString()
                            NewInvoiceItemHeaders.OrgPrice -> item.orgPrice.toString()
                            NewInvoiceItemHeaders.ItemDisc -> item.itemDisc.toString()
                            NewInvoiceItemHeaders.Price -> item.price.toString()
                            NewInvoiceItemHeaders.ExtPrice -> item.extPrice.toString()
                            NewInvoiceItemHeaders.PriceWOT -> item.priceWOT.toString()
                            NewInvoiceItemHeaders.TaxPercentage -> item.taxPerc.toString()
                            NewInvoiceItemHeaders.TaxAmount -> item.taxAmount.toString()
                            NewInvoiceItemHeaders.ItemSerial -> item.itemSerial.toString()
                        }
                        if (header == NewInvoiceItemHeaders.Qty) {
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .width(with(LocalDensity.current) {
                                        (biggestColumnWidths[header] ?: 0).toDp()
                                    }),
                                contentAlignment = Alignment.Center
                            ) {
                                BasicTextField(
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    value = content,
                                    onValueChange = {
                                        if (it.isEmpty())
                                            onChangeQty("0", item.itemID)
                                        else
                                            onChangeQty(it, item.itemID)
                                    },
                                    maxLines = 1,
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        } else {
                            itemBox(
                                content = content,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .width(with(LocalDensity.current) {
                                        (biggestColumnWidths[header] ?: 0).toDp()
                                    })
                            )
                        }
                    }
                }
            }


            Spacer(Modifier.fillMaxWidth().background(Color.LightGray).height(0.5.dp))
        }
    }
}


@Composable
private fun calculateBiggestWidths(invoiceItems: List<NewInvoiceItemUiState>): SnapshotStateMap<NewInvoiceItemHeaders, Int> {
    val biggestColumnWidths = remember { mutableStateMapOf<NewInvoiceItemHeaders, Int>() }
    biggestColumnWidths[NewInvoiceItemHeaders.ItemCode] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemCode.toString() } + getTitleByHeader(NewInvoiceItemHeaders.ItemCode))
    biggestColumnWidths[NewInvoiceItemHeaders.Alu] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.alu.toString() } + getTitleByHeader(NewInvoiceItemHeaders.Alu))
    biggestColumnWidths[NewInvoiceItemHeaders.Name] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.name.toString() } + getTitleByHeader(NewInvoiceItemHeaders.Name))
    biggestColumnWidths[NewInvoiceItemHeaders.Qty] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.qty } + getTitleByHeader(NewInvoiceItemHeaders.Qty))
    biggestColumnWidths[NewInvoiceItemHeaders.OrgPrice] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.orgPrice.toString() } + getTitleByHeader(NewInvoiceItemHeaders.OrgPrice))
    biggestColumnWidths[NewInvoiceItemHeaders.ItemDisc] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemDisc.toString() } + getTitleByHeader(NewInvoiceItemHeaders.ItemDisc))
    biggestColumnWidths[NewInvoiceItemHeaders.Price] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.price.toString() } + getTitleByHeader(NewInvoiceItemHeaders.Price))
    biggestColumnWidths[NewInvoiceItemHeaders.ExtPrice] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.extPrice.toString() } + getTitleByHeader(NewInvoiceItemHeaders.ExtPrice))
    biggestColumnWidths[NewInvoiceItemHeaders.PriceWOT] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.priceWOT.toString() } + getTitleByHeader(NewInvoiceItemHeaders.PriceWOT))
    biggestColumnWidths[NewInvoiceItemHeaders.TaxPercentage] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.taxPerc.toString() } + getTitleByHeader(NewInvoiceItemHeaders.TaxPercentage))
    biggestColumnWidths[NewInvoiceItemHeaders.TaxAmount] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.taxAmount.toString() } + getTitleByHeader(NewInvoiceItemHeaders.TaxAmount))
    biggestColumnWidths[NewInvoiceItemHeaders.ItemSerial] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemSerial.toString() } + getTitleByHeader(NewInvoiceItemHeaders.ItemSerial))
    return biggestColumnWidths
}

@Composable
private fun getTitleByHeader(header: NewInvoiceItemHeaders): String {
    return when (header) {
        NewInvoiceItemHeaders.ItemCode -> Resources.strings.itemCode
        NewInvoiceItemHeaders.Alu -> Resources.strings.alu
        NewInvoiceItemHeaders.Name -> Resources.strings.name
        NewInvoiceItemHeaders.Qty -> Resources.strings.qty
        NewInvoiceItemHeaders.OrgPrice -> Resources.strings.orgPrice
        NewInvoiceItemHeaders.ItemDisc -> Resources.strings.itemDisc
        NewInvoiceItemHeaders.Price -> Resources.strings.price
        NewInvoiceItemHeaders.ExtPrice -> Resources.strings.extPrice
        NewInvoiceItemHeaders.PriceWOT -> Resources.strings.priceWOT
        NewInvoiceItemHeaders.TaxPercentage -> Resources.strings.taxPercentage
        NewInvoiceItemHeaders.TaxAmount -> Resources.strings.taxAmount
        NewInvoiceItemHeaders.ItemSerial -> Resources.strings.itemSerial
    }
}

private enum class NewInvoiceItemHeaders {
    ItemCode,
    Alu,
    Name,
    Qty,
    OrgPrice,
    ItemDisc,
    Price,
    ExtPrice,
    PriceWOT,
    TaxPercentage,
    TaxAmount,
    ItemSerial
}