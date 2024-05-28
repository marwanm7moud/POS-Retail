package org.abapps.app.presentation.screens.invoiceScreen.composables

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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.abapps.app.presentation.screens.composable.itemBox
import org.abapps.app.presentation.screens.invoiceScreen.NewInvoiceItemUiState
import org.abapps.app.util.calculateBiggestWidthOnEveryRow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewInvoiceItemTable(
    invoiceItems: List<NewInvoiceItemUiState>,
    selectedItemIndex: Int,
    onClickItem: (Int) -> Unit,
    onClickItemDelete: (Int) -> Unit,
    onClickItemEdit: (Int) -> Unit,
    onClickItemDiscount: (Int) -> Unit,
    modifier: Modifier
) {
    val headers = NewInvoiceItemHeaders.entries.toTypedArray()
    val biggestColumnWidths = calculateBiggestWidths(invoiceItems)



    Column(
        modifier = modifier.padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            //.defaultMinSize(minHeight = 120.dp)
            .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(12.dp))
            .horizontalScroll(rememberScrollState(0))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.background(color = Color.LightGray).padding(vertical = 8.dp),
        ) {
            headers.forEach { header ->
                itemBox(
                    content = header.title,
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
                            onClickItemDelete(index)
                        },
                        text = {
                            Text(text = "Delete")
                        },
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onClickItemEdit(index)
                        },
                        text = {
                            Text(text = "Edit")
                        },
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onClickItemDiscount(index)
                        },
                        text = {
                            Text(text = "Discount")
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
                            NewInvoiceItemHeaders.Number -> (index + 1).toString()
                        }
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
            

            Spacer(Modifier.fillMaxWidth().background(Color.LightGray).height(0.5.dp))
        }
    }
}


@Composable
private fun calculateBiggestWidths(invoiceItems: List<NewInvoiceItemUiState>): SnapshotStateMap<NewInvoiceItemHeaders, Int> {
    val biggestColumnWidths = remember { mutableStateMapOf<NewInvoiceItemHeaders, Int>() }
    biggestColumnWidths[NewInvoiceItemHeaders.ItemCode] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemCode.toString() } + NewInvoiceItemHeaders.ItemCode.title)
    biggestColumnWidths[NewInvoiceItemHeaders.Number] =
        calculateBiggestWidthOnEveryRow(listOf(NewInvoiceItemHeaders.Number.title, "19"))
    biggestColumnWidths[NewInvoiceItemHeaders.Alu] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.alu.toString() } + NewInvoiceItemHeaders.Alu.title)
    biggestColumnWidths[NewInvoiceItemHeaders.Name] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.name.toString() } + NewInvoiceItemHeaders.Name.title)
    biggestColumnWidths[NewInvoiceItemHeaders.Qty] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.qty.toString() } + NewInvoiceItemHeaders.Qty.title)
    biggestColumnWidths[NewInvoiceItemHeaders.OrgPrice] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.orgPrice.toString() } + NewInvoiceItemHeaders.OrgPrice.title)
    biggestColumnWidths[NewInvoiceItemHeaders.ItemDisc] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemDisc.toString() } + NewInvoiceItemHeaders.ItemDisc.title)
    biggestColumnWidths[NewInvoiceItemHeaders.Price] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.price.toString() } + NewInvoiceItemHeaders.Price.title)
    biggestColumnWidths[NewInvoiceItemHeaders.ExtPrice] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.extPrice.toString() } + NewInvoiceItemHeaders.ExtPrice.title)
    biggestColumnWidths[NewInvoiceItemHeaders.PriceWOT] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.priceWOT.toString() } + NewInvoiceItemHeaders.PriceWOT.title)
    biggestColumnWidths[NewInvoiceItemHeaders.TaxPercentage] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.taxPerc.toString() } + NewInvoiceItemHeaders.TaxPercentage.title)
    biggestColumnWidths[NewInvoiceItemHeaders.TaxAmount] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.taxAmount.toString() } + NewInvoiceItemHeaders.TaxAmount.title)
    biggestColumnWidths[NewInvoiceItemHeaders.ItemSerial] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemSerial.toString() } + NewInvoiceItemHeaders.ItemSerial.title)
    return biggestColumnWidths
}


private enum class NewInvoiceItemHeaders(val title: String) {
    Number("No."),
    ItemCode("Item Code"),
    Alu("Alu"),
    Name("Name"),
    Qty("Qty"),
    OrgPrice("OrgPrice"),
    ItemDisc("ItemDisc"),
    Price("Price"),
    ExtPrice("ExtPrice"),
    PriceWOT("PriceWOT"),
    TaxPercentage("TaxPerc"),
    TaxAmount("TaxAmount"),
    ItemSerial("ItemSerial")
}