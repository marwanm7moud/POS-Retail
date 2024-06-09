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
import org.abapps.app.resource.Resources
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
                            onClickItemCopy(index)
                        },
                        text = {
                            Text(text = Resources.strings.copy)
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
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.fromStore } + getTitleByHeader(TInvoicesHeaders.fromStore))

    biggestColumnWidths[TInvoicesHeaders.toStore] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.toStore } + getTitleByHeader(TInvoicesHeaders.toStore))
    biggestColumnWidths[TInvoicesHeaders.transType] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.transType } + getTitleByHeader(TInvoicesHeaders.transType))
    biggestColumnWidths[TInvoicesHeaders.transDate] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.transDate } + getTitleByHeader(TInvoicesHeaders.transDate))

    biggestColumnWidths[TInvoicesHeaders.status] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.status } + getTitleByHeader(TInvoicesHeaders.status))
    biggestColumnWidths[TInvoicesHeaders.comment] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.comment } + getTitleByHeader(TInvoicesHeaders.comment))

    biggestColumnWidths[TInvoicesHeaders.createDate] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.createDate } + getTitleByHeader(TInvoicesHeaders.createDate))
    biggestColumnWidths[TInvoicesHeaders.totQtyTran] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.totQtyTran.toString() } + getTitleByHeader(TInvoicesHeaders.totQtyTran))
    biggestColumnWidths[TInvoicesHeaders.sentByUser] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.sentByUser } + getTitleByHeader(TInvoicesHeaders.sentByUser))

    biggestColumnWidths[TInvoicesHeaders.ws] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.ws.toString() } + getTitleByHeader(TInvoicesHeaders.ws))
    biggestColumnWidths[TInvoicesHeaders.indexId] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.indexId.toString() } + getTitleByHeader(TInvoicesHeaders.indexId))

    biggestColumnWidths[TInvoicesHeaders.isReceived] =
        calculateBiggestWidthOnEveryRow(listOf(getTitleByHeader(TInvoicesHeaders.isReceived)))
    biggestColumnWidths[TInvoicesHeaders.isHold] =
        calculateBiggestWidthOnEveryRow(listOf(getTitleByHeader(TInvoicesHeaders.isHold)))
    biggestColumnWidths[TInvoicesHeaders.isPost] =
        calculateBiggestWidthOnEveryRow(listOf(getTitleByHeader(TInvoicesHeaders.isPost)))
    return biggestColumnWidths
}

@Composable
private fun getTitleByHeader(header:TInvoicesHeaders): String{
    return when (header) {
        TInvoicesHeaders.fromStore -> Resources.strings.fromStore
        TInvoicesHeaders.toStore -> Resources.strings.toStore
        TInvoicesHeaders.transType -> Resources.strings.transType
        TInvoicesHeaders.transDate -> Resources.strings.transDate
        TInvoicesHeaders.status ->Resources.strings.status
        TInvoicesHeaders.comment -> Resources.strings.comment
        TInvoicesHeaders.isReceived -> Resources.strings.isReceived
        TInvoicesHeaders.isHold -> Resources.strings.isHold
        TInvoicesHeaders.isPost -> Resources.strings.isPost
        TInvoicesHeaders.createDate -> Resources.strings.createDate
        TInvoicesHeaders.totQtyTran -> Resources.strings.totQtyTran
        TInvoicesHeaders.sentByUser -> Resources.strings.sentByUser
        TInvoicesHeaders.ws -> Resources.strings.ws
        TInvoicesHeaders.indexId -> Resources.strings.indexId
    }
}
private enum class TInvoicesHeaders {
    fromStore,
    toStore,
    transType,
    transDate,
    status,
    comment,
    isReceived,
    isHold,
    isPost,
    createDate,
    totQtyTran,
    sentByUser,
    ws,
    indexId,
}
