package org.abapps.app.presentation.screens.transferInvoices.composables

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
import org.abapps.app.presentation.screens.transferInvoices.TInvoiceUiState
import org.abapps.app.util.calculateBiggestWidthOnEveryRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransferInvoicesTable(
    invoiceItems: List<TInvoiceUiState>,
    selectedItemIndex: Int,
    onClickItem: (Int) -> Unit,
    onClickItemDelete: (Int) -> Unit,
    onClickItemEdit: (Int) -> Unit,
    onClickItemCopy: (Int) -> Unit,
    modifier: Modifier
) {
    val headers = TInvoicesHeaders.entries.toTypedArray()
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
                            onClickItemCopy(index)
                        },
                        text = {
                            Text(text = "Copy")
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
                            TInvoicesHeaders.fromStore -> item.fromStore.toString()
                            TInvoicesHeaders.toStore -> item.toStore.toString()
                            TInvoicesHeaders.transType -> item.transType.toString()
                            TInvoicesHeaders.transDate -> item.transDate.toString()
                            TInvoicesHeaders.status -> item.status.toString()
                            TInvoicesHeaders.comment -> item.comment.toString()
                            TInvoicesHeaders.isReceived -> item.isReceived.toString()
                            TInvoicesHeaders.isHold -> item.isHold.toString()
                            TInvoicesHeaders.isPost -> item.isPost.toString()
                            TInvoicesHeaders.createDate -> item.createDate.toString()
                            TInvoicesHeaders.totQtyTran -> item.totQtyTran.toString()
                            TInvoicesHeaders.sentByUser -> item.sentByUser.toString()
                            TInvoicesHeaders.ws -> item.ws.toString()
                            TInvoicesHeaders.indexId -> item.indexId.toString()
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
private fun calculateBiggestWidths(invoiceItems: List<TInvoiceUiState>): SnapshotStateMap<TInvoicesHeaders, Int> {
    val biggestColumnWidths = remember { mutableStateMapOf<TInvoicesHeaders, Int>() }

    biggestColumnWidths[TInvoicesHeaders.fromStore] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.fromStore.toString() } + TInvoicesHeaders.fromStore.title)

    biggestColumnWidths[TInvoicesHeaders.toStore] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.toStore.toString() } + TInvoicesHeaders.toStore.title)
    biggestColumnWidths[TInvoicesHeaders.transType] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.transType.toString() } + TInvoicesHeaders.transType.title)
    biggestColumnWidths[TInvoicesHeaders.transDate] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.transDate.toString() } + TInvoicesHeaders.transDate.title)

    biggestColumnWidths[TInvoicesHeaders.status] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.status.toString() } + TInvoicesHeaders.status.title)
    biggestColumnWidths[TInvoicesHeaders.comment] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.comment.toString() } + TInvoicesHeaders.comment.title)

    biggestColumnWidths[TInvoicesHeaders.createDate] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.createDate.toString() } + TInvoicesHeaders.createDate.title)
    biggestColumnWidths[TInvoicesHeaders.totQtyTran] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.totQtyTran.toString() } + TInvoicesHeaders.totQtyTran.title)
    biggestColumnWidths[TInvoicesHeaders.sentByUser] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.sentByUser.toString() } + TInvoicesHeaders.sentByUser.title)

    biggestColumnWidths[TInvoicesHeaders.ws] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.ws.toString() } + TInvoicesHeaders.ws.title)
    biggestColumnWidths[TInvoicesHeaders.indexId] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.indexId.toString() } + TInvoicesHeaders.indexId.title)

    biggestColumnWidths[TInvoicesHeaders.isReceived] =
        calculateBiggestWidthOnEveryRow(listOf(TInvoicesHeaders.isReceived.title))
    biggestColumnWidths[TInvoicesHeaders.isHold] =
        calculateBiggestWidthOnEveryRow(listOf(TInvoicesHeaders.isHold.title))
    biggestColumnWidths[TInvoicesHeaders.isPost] =
        calculateBiggestWidthOnEveryRow(listOf(TInvoicesHeaders.isPost.title))
    return biggestColumnWidths
}


private enum class TInvoicesHeaders(val title: String) {
    fromStore("From_Store"),
    toStore("To_Store"),
    transType("Tran_Type"),
    transDate("Tran_Date"),
    status("Status"),
    comment("Comment"),
    isReceived("Received"),
    isHold("Hold"),
    isPost("Post"),
    createDate("Create_Date"),
    totQtyTran("TOT_Qty_Tran"),
    sentByUser("Sent_By_User"),
    ws("WS"),
    indexId("Index_Id"),
}
