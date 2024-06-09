package org.abapps.app.presentation.screens.allinvoices.composables

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
import org.abapps.app.presentation.screens.allinvoices.InvoiceUiState
import org.abapps.app.presentation.screens.composable.itemBox
import org.abapps.app.resource.Resources
import org.abapps.app.util.calculateBiggestWidthOnEveryRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InvoicesItemTable(
    invoiceItems: List<InvoiceUiState>,
    selectedItemIndex: Int,
    onClickItem: (Int) -> Unit,
    onClickItemDelete: (Int) -> Unit,
    onClickItemEdit: (Int) -> Unit,
    onClickItemCopy: (Int) -> Unit,
    modifier: Modifier
) {
    val headers = InvoicesHeaders.entries.toTypedArray()
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
                            InvoicesHeaders.invId -> item.invId.toString()
                            InvoicesHeaders.invNumber -> item.invNumber.toString()
                            InvoicesHeaders.invType -> item.invType.toString()
                            InvoicesHeaders.status -> item.status.toString()
                            InvoicesHeaders.sourceType -> item.sourceType.toString()
                            InvoicesHeaders.comment -> item.comment.toString()
                            InvoicesHeaders.hold -> item.hold.toString()
                            InvoicesHeaders.invDate -> item.invDate.toString()
                            InvoicesHeaders.post -> item.post.toString()
                            InvoicesHeaders.postedDate -> item.postedDate.toString()
                            InvoicesHeaders.custId -> item.custId.toString()
                            InvoicesHeaders.firstName -> item.firstName.toString()
                            InvoicesHeaders.cashierId -> item.cashierId.toString()
                            InvoicesHeaders.cashierName -> item.cashierName.toString()
                            InvoicesHeaders.amt -> item.amt.toString()
                            InvoicesHeaders.createDate -> item.createDate.toString()
                            InvoicesHeaders.sComId -> item.sComId.toString()
                            InvoicesHeaders.storeId -> item.storeId.toString()
                            InvoicesHeaders.ws -> item.ws.toString()
                            InvoicesHeaders.soId -> item.soId.toString()
                            InvoicesHeaders.reverse -> item.reverse.toString()
                            InvoicesHeaders.indexId -> item.indexId.toString()
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
private fun calculateBiggestWidths(invoiceItems: List<InvoiceUiState>): SnapshotStateMap<InvoicesHeaders, Int> {
    val biggestColumnWidths = remember { mutableStateMapOf<InvoicesHeaders, Int>() }

    biggestColumnWidths[InvoicesHeaders.invId] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.invId.toString() } + getTitleByHeader(InvoicesHeaders.invId))
    biggestColumnWidths[InvoicesHeaders.invNumber] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.invNumber.toString() } + getTitleByHeader(InvoicesHeaders.invNumber))
    biggestColumnWidths[InvoicesHeaders.invType] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.invType.toString() } + getTitleByHeader(InvoicesHeaders.invType))
    biggestColumnWidths[InvoicesHeaders.status] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.status.toString() } + getTitleByHeader(InvoicesHeaders.status))
    biggestColumnWidths[InvoicesHeaders.sourceType] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.sourceType.toString() } + getTitleByHeader(InvoicesHeaders.sourceType))
    biggestColumnWidths[InvoicesHeaders.invNumber] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.invNumber.toString() } + getTitleByHeader(InvoicesHeaders.invNumber))
    biggestColumnWidths[InvoicesHeaders.comment] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.comment.toString() } + getTitleByHeader(InvoicesHeaders.comment))
    biggestColumnWidths[InvoicesHeaders.hold] =
        calculateBiggestWidthOnEveryRow(listOf(getTitleByHeader(InvoicesHeaders.comment)))
    biggestColumnWidths[InvoicesHeaders.invDate] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.invDate.toString() } + getTitleByHeader(InvoicesHeaders.invDate))
    biggestColumnWidths[InvoicesHeaders.post] =
        calculateBiggestWidthOnEveryRow(listOf(getTitleByHeader(InvoicesHeaders.post)))
    biggestColumnWidths[InvoicesHeaders.postedDate] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.postedDate.toString() } + getTitleByHeader(InvoicesHeaders.postedDate))
    biggestColumnWidths[InvoicesHeaders.custId] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.custId.toString() } + getTitleByHeader(InvoicesHeaders.custId))
    biggestColumnWidths[InvoicesHeaders.firstName] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.firstName.toString() } + getTitleByHeader(InvoicesHeaders.firstName))
    biggestColumnWidths[InvoicesHeaders.cashierId] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.cashierId.toString() } + getTitleByHeader(InvoicesHeaders.cashierId))
    biggestColumnWidths[InvoicesHeaders.cashierName] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.cashierName.toString() } + getTitleByHeader(InvoicesHeaders.cashierName))
    biggestColumnWidths[InvoicesHeaders.amt] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.amt.toString() } + getTitleByHeader(InvoicesHeaders.amt))
    biggestColumnWidths[InvoicesHeaders.createDate] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.createDate.toString() } + getTitleByHeader(InvoicesHeaders.createDate))
    biggestColumnWidths[InvoicesHeaders.sComId] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.sComId.toString() } + getTitleByHeader(InvoicesHeaders.sComId))
    biggestColumnWidths[InvoicesHeaders.storeId] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.storeId.toString() } + getTitleByHeader(InvoicesHeaders.storeId))
    biggestColumnWidths[InvoicesHeaders.ws] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.ws.toString() } + getTitleByHeader(InvoicesHeaders.ws))
    biggestColumnWidths[InvoicesHeaders.reverse] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.reverse.toString() } + getTitleByHeader(InvoicesHeaders.reverse))
    biggestColumnWidths[InvoicesHeaders.indexId] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.indexId.toString() } + getTitleByHeader(InvoicesHeaders.indexId))
    biggestColumnWidths[InvoicesHeaders.soId] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.soId.toString() } + getTitleByHeader(InvoicesHeaders.soId))
    return biggestColumnWidths
}

@Composable
private fun getTitleByHeader(header: InvoicesHeaders): String {
    return when (header) {
        InvoicesHeaders.invId -> Resources.strings.invId
        InvoicesHeaders.invNumber -> Resources.strings.invNumber
        InvoicesHeaders.invType -> Resources.strings.invType
        InvoicesHeaders.status -> Resources.strings.status
        InvoicesHeaders.comment -> Resources.strings.comment
        InvoicesHeaders.sourceType -> Resources.strings.sourceType
        InvoicesHeaders.hold -> Resources.strings.isHold
        InvoicesHeaders.invDate -> Resources.strings.invDate
        InvoicesHeaders.post -> Resources.strings.isPost
        InvoicesHeaders.postedDate -> Resources.strings.postedDate
        InvoicesHeaders.custId -> Resources.strings.custId
        InvoicesHeaders.firstName -> Resources.strings.firstName
        InvoicesHeaders.cashierId -> Resources.strings.cashierId
        InvoicesHeaders.cashierName -> Resources.strings.cashierName
        InvoicesHeaders.amt -> Resources.strings.amt
        InvoicesHeaders.createDate -> Resources.strings.createDate
        InvoicesHeaders.sComId -> Resources.strings.sComId
        InvoicesHeaders.storeId -> Resources.strings.storeId
        InvoicesHeaders.soId -> Resources.strings.soId
        InvoicesHeaders.reverse -> Resources.strings.reverse
        InvoicesHeaders.ws -> Resources.strings.ws
        InvoicesHeaders.indexId -> Resources.strings.indexId
    }
}

private enum class InvoicesHeaders {
    invId,
    invNumber,
    invType,
    status,
    sourceType,
    comment,
    hold,
    invDate,
    post,
    postedDate,
    custId,
    firstName,
    cashierId,
    cashierName,
    amt,
    createDate,
    sComId,
    storeId,
    ws,
    soId,
    reverse,
    indexId,
}