package org.abapps.app.presentation.screens.transferNewInvoice.composables

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
import org.abapps.app.presentation.screens.transferNewInvoice.TransferNewInvoiceItemUiState
import org.abapps.app.resource.Resources
import org.abapps.app.util.calculateBiggestWidthOnEveryRow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TransferInvoiceItemTable(
    invoiceItems: List<TransferNewInvoiceItemUiState>,
    selectedItemIndex: Int,
    onClickItem: (Int) -> Unit,
    onClickItemDelete: (Int) -> Unit,
    onClickItemEdit: (Int) -> Unit,
    onClickItemDiscount: (Int) -> Unit,
    modifier: Modifier
) {
    val headers = TransferNewInvoiceItemHeaders.entries.toTypedArray()
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
                            onClickItemDelete(index)
                        },
                        text = {
                            Text(text = Resources.strings.delete)
                        },
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onClickItemEdit(index)
                        },
                        text = {
                            Text(text = Resources.strings.edit)
                        },
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onClickItemDiscount(index)
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
                            TransferNewInvoiceItemHeaders.itemCode -> item.itemCode.toString()
                            TransferNewInvoiceItemHeaders.alu -> item.alu.toString()
                            TransferNewInvoiceItemHeaders.qtyOH -> item.qtyOH.toString()
                            TransferNewInvoiceItemHeaders.qtyTran -> item.qtyTran.toString()
                            TransferNewInvoiceItemHeaders.comment -> item.comment.toString()
                            TransferNewInvoiceItemHeaders.cost -> item.cost.toString()
                            TransferNewInvoiceItemHeaders.price -> item.price.toString()
                            TransferNewInvoiceItemHeaders.UDF11 -> item.UDF11.toString()
                            TransferNewInvoiceItemHeaders.UDF12 -> item.UDF12.toString()
                            TransferNewInvoiceItemHeaders.Name -> item.name.toString()
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
private fun calculateBiggestWidths(invoiceItems: List<TransferNewInvoiceItemUiState>): SnapshotStateMap<TransferNewInvoiceItemHeaders, Int> {
    val biggestColumnWidths = remember { mutableStateMapOf<TransferNewInvoiceItemHeaders, Int>() }
    biggestColumnWidths[TransferNewInvoiceItemHeaders.itemCode] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemCode.toString() } + getTitleByHeader(TransferNewInvoiceItemHeaders.itemCode))
    biggestColumnWidths[TransferNewInvoiceItemHeaders.alu] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.alu.toString() } + getTitleByHeader(TransferNewInvoiceItemHeaders.alu))
    biggestColumnWidths[TransferNewInvoiceItemHeaders.Name] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.name.toString() } + getTitleByHeader(TransferNewInvoiceItemHeaders.Name))

    biggestColumnWidths[TransferNewInvoiceItemHeaders.qtyOH] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.qtyOH.toString() } + getTitleByHeader(TransferNewInvoiceItemHeaders.qtyOH))



    biggestColumnWidths[TransferNewInvoiceItemHeaders.qtyTran] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.qtyTran.toString() } + getTitleByHeader(TransferNewInvoiceItemHeaders.qtyTran))

    biggestColumnWidths[TransferNewInvoiceItemHeaders.comment] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.comment.toString() } + getTitleByHeader(TransferNewInvoiceItemHeaders.comment))

    biggestColumnWidths[TransferNewInvoiceItemHeaders.cost] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.cost.toString() } + getTitleByHeader(TransferNewInvoiceItemHeaders.cost))

    biggestColumnWidths[TransferNewInvoiceItemHeaders.price] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.price.toString() } + getTitleByHeader(TransferNewInvoiceItemHeaders.price))

    biggestColumnWidths[TransferNewInvoiceItemHeaders.UDF11] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.UDF11.toString() } + getTitleByHeader(TransferNewInvoiceItemHeaders.UDF11))
    biggestColumnWidths[TransferNewInvoiceItemHeaders.UDF12] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.UDF12.toString() } + getTitleByHeader(TransferNewInvoiceItemHeaders.UDF12))
    return biggestColumnWidths
}

@Composable
private fun getTitleByHeader(header: TransferNewInvoiceItemHeaders): String{
    return when (header) {
        TransferNewInvoiceItemHeaders.itemCode -> Resources.strings.itemCode
        TransferNewInvoiceItemHeaders.alu -> "Alu"
        TransferNewInvoiceItemHeaders.Name -> Resources.strings.name
        TransferNewInvoiceItemHeaders.qtyOH -> Resources.strings.qtyOnHand
        TransferNewInvoiceItemHeaders.qtyTran ->Resources.strings.qtyTransfer
        TransferNewInvoiceItemHeaders.comment -> Resources.strings.comment
        TransferNewInvoiceItemHeaders.cost -> Resources.strings.cost
        TransferNewInvoiceItemHeaders.price -> Resources.strings.price
        TransferNewInvoiceItemHeaders.UDF11 -> "UDF11"
        TransferNewInvoiceItemHeaders.UDF12 -> "UDF12"
    }
}
private enum class TransferNewInvoiceItemHeaders {
    itemCode,
    alu,
    Name,
    qtyOH,
    qtyTran,
    comment,
    cost,
    price,
    UDF11,
    UDF12

}