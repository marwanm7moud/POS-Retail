package org.abapps.app.presentation.screens.posInvoiceScreen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.presentation.screens.composable.ErrorItem
import org.abapps.app.presentation.screens.composable.ErrorView
import org.abapps.app.presentation.screens.composable.convertLazyPagingItemsToList
import org.abapps.app.presentation.screens.composable.itemBox
import org.abapps.app.presentation.screens.posInvoiceScreen.ItemUiState
import org.abapps.app.util.calculateBiggestWidthOnEveryRow

@Composable
fun AllItemTable(
    modifier: Modifier,
    invoiceItems: LazyPagingItems<ItemUiState>,
    selectedItemIndex: List<Int>,
    onClickItem: (Int) -> Unit
) {
    val headers = AllItemHeaders.entries.toTypedArray()
    val items = convertLazyPagingItemsToList(invoiceItems)
    val biggestColumnWidths = calculateBiggestWidths(items)

    Column(
        modifier = modifier.padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .defaultMinSize(minHeight = 120.dp)
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
        LazyColumn {
            items(invoiceItems.itemCount) { index ->
                val item = invoiceItems[index]
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.background(
                        color = if (selectedItemIndex.contains(index)) Color(
                            0xFF383a3d
                        ) else Color.Gray
                    ).clickable { onClickItem(index) }.padding(vertical = 4.dp)
                ) {
                    headers.forEach { header ->
                        val content = when (header) {
                            AllItemHeaders.ITEM_ID -> item?.itemID.toString()
                            AllItemHeaders.ITEM_CODE -> item?.itemCode.toString()
                            AllItemHeaders.UPC -> item?.upc.toString()
                            AllItemHeaders.ALU -> item?.alu.toString()
                            AllItemHeaders.NAME -> item?.name.toString()
                            AllItemHeaders.NAME2 -> item?.name2.toString()
                            AllItemHeaders.DESCRIPTION -> item?.description.toString()
                            AllItemHeaders.STYLE_ID -> item?.styleId.toString()
                            AllItemHeaders.STYLE_NAME -> item?.styleName.toString()
                            AllItemHeaders.ON_HAND -> item?.onHand.toString()
                            AllItemHeaders.FREE_CARD -> item?.freeCard.toString()
                            AllItemHeaders.FREE_CARD_PRICE -> item?.freeCardPrice.toString()
                            AllItemHeaders.PRICE -> item?.price.toString()
                            AllItemHeaders.QTY -> item?.qty.toString()
                            AllItemHeaders.ITEM_DISCOUNT -> item?.itemDiscount.toString()
                            AllItemHeaders.GRID1 -> item?.grid1.toString()
                            AllItemHeaders.GRID2 -> item?.grid2.toString()
                            AllItemHeaders.GRID3 -> item?.grid3.toString()
                            AllItemHeaders.VEND_ID -> item?.vendId.toString()
                            AllItemHeaders.VEND_NAME -> item?.vendName.toString()
                            AllItemHeaders.NON_INVN -> item?.nonInvn.toString()
                            AllItemHeaders.TAXABLE -> item?.taxable.toString()
                            AllItemHeaders.TAX_ID -> item?.taxId.toString()
                            AllItemHeaders.ITEM_TYPE -> item?.itemType.toString()
                            AllItemHeaders.SCLASS_ID -> item?.sClassId.toString()
                            AllItemHeaders.SUB_CLASS -> item?.subClass.toString()
                            AllItemHeaders.CLASS_ID -> item?.classId.toString()
                            AllItemHeaders.CLASS_NAME -> item?.classId.toString()
                            AllItemHeaders.DEPT_ID -> item?.deptId.toString()
                            AllItemHeaders.DEPARTMENT -> item?.department.toString()
                            AllItemHeaders.ACTIVE -> item?.active.toString()
                            AllItemHeaders.OPEN_PRICE -> item?.openPrice.toString()
                            AllItemHeaders.UDF1 -> item?.UDF1.toString()
                            AllItemHeaders.UDF2 -> item?.UDF2.toString()
                            AllItemHeaders.UDF3 -> item?.UDF3.toString()
                            AllItemHeaders.UDF4 -> item?.UDF4.toString()
                            AllItemHeaders.UDF5 -> item?.UDF5.toString()
                            AllItemHeaders.UDF6 -> item?.UDF6.toString()
                            AllItemHeaders.UDF7 -> item?.UDF7.toString()
                            AllItemHeaders.UDF8 -> item?.UDF9.toString()
                            AllItemHeaders.UDF9 -> item?.UDF9.toString()
                            AllItemHeaders.UDF10 -> item?.UDF10.toString()
                            AllItemHeaders.UDF11 -> item?.UDF11.toString()
                            AllItemHeaders.UDF12 -> item?.UDF12.toString()
                            AllItemHeaders.UDF13 -> item?.UDF13.toString()
                            AllItemHeaders.UDF14 -> item?.UDF14.toString()
                            AllItemHeaders.UDF15 -> item?.UDF15.toString()
                            AllItemHeaders.UDF16 -> item?.UDF16.toString()
                            AllItemHeaders.UDF17 -> item?.UDF17.toString()
                            AllItemHeaders.UDF18 -> item?.UDF18.toString()
                            AllItemHeaders.UDF19 -> item?.UDF19.toString()
                            AllItemHeaders.UDF20 -> item?.UDF20.toString()
                            AllItemHeaders.INDEX_ID -> item?.indexId.toString()
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
                Spacer(Modifier.fillMaxWidth().background(Color.LightGray).height(0.5.dp))
            }
            invoiceItems.loadState.apply {
                when {
                    refresh is LoadStateNotLoading && invoiceItems.itemCount < 1 -> {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No Items",
                                    modifier = Modifier.align(Alignment.Center),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    refresh is LoadStateLoading -> {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier.align(Alignment.Center),
                                    color = Theme.colors.primary
                                )
                            }
                        }
                    }

                    append is LoadStateLoading -> {
                        item {
                            CircularProgressIndicator(
                                color = Theme.colors.primary,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(16.dp)
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    refresh is LoadStateError -> {
                        item {
                            ErrorView(
                                message = "No Internet Connection",
                                onClickRetry = { invoiceItems.retry() },
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                    }

                    append is LoadStateError -> {
                        item {
                            ErrorItem(
                                message = "No Internet Connection",
                                onClickRetry = { invoiceItems.retry() },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun calculateBiggestWidths(invoiceItems: List<ItemUiState>): SnapshotStateMap<AllItemHeaders, Int> {
    val biggestColumnWidths = remember { mutableStateMapOf<AllItemHeaders, Int>() }
    biggestColumnWidths[AllItemHeaders.ITEM_ID] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemID.toString() } + AllItemHeaders.ITEM_ID.title)
    biggestColumnWidths[AllItemHeaders.ITEM_CODE] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemCode.toString() } + AllItemHeaders.ITEM_CODE.title)
    biggestColumnWidths[AllItemHeaders.UPC] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.upc.toString() } + AllItemHeaders.UPC.title)
    biggestColumnWidths[AllItemHeaders.ALU] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.alu.toString() } + AllItemHeaders.ALU.title)
    biggestColumnWidths[AllItemHeaders.NAME] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.name.toString() } + AllItemHeaders.NAME.title)
    biggestColumnWidths[AllItemHeaders.NAME2] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.name2.toString() } + AllItemHeaders.NAME2.title)
    biggestColumnWidths[AllItemHeaders.DESCRIPTION] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.description.toString() } + AllItemHeaders.DESCRIPTION.title)
    biggestColumnWidths[AllItemHeaders.STYLE_ID] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.styleId.toString() } + AllItemHeaders.STYLE_ID.title)
    biggestColumnWidths[AllItemHeaders.STYLE_NAME] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.styleName.toString() } + AllItemHeaders.STYLE_NAME.title)
    biggestColumnWidths[AllItemHeaders.ON_HAND] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.onHand.toString() } + AllItemHeaders.ON_HAND.title)
    biggestColumnWidths[AllItemHeaders.FREE_CARD] = calculateBiggestWidthOnEveryRow(
        listOf(
            AllItemHeaders.FREE_CARD.title
        )
    )
    biggestColumnWidths[AllItemHeaders.FREE_CARD_PRICE] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.freeCardPrice.toString() } + AllItemHeaders.FREE_CARD_PRICE.title)
    biggestColumnWidths[AllItemHeaders.PRICE] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.price.toString() } + AllItemHeaders.PRICE.title)
    biggestColumnWidths[AllItemHeaders.QTY] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.qty.toString() } + AllItemHeaders.QTY.title)
    biggestColumnWidths[AllItemHeaders.ITEM_DISCOUNT] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemDiscount.toString() } + AllItemHeaders.ITEM_DISCOUNT.title)
    biggestColumnWidths[AllItemHeaders.GRID1] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.grid1.toString() } + AllItemHeaders.GRID1.title)
    biggestColumnWidths[AllItemHeaders.GRID2] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.grid2.toString() } + AllItemHeaders.GRID2.title)
    biggestColumnWidths[AllItemHeaders.GRID3] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.grid3.toString() } + AllItemHeaders.GRID3.title)
    biggestColumnWidths[AllItemHeaders.VEND_ID] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.vendId.toString() } + AllItemHeaders.VEND_ID.title)
    biggestColumnWidths[AllItemHeaders.VEND_NAME] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.vendName.toString() } + AllItemHeaders.VEND_NAME.title)
    biggestColumnWidths[AllItemHeaders.NON_INVN] = calculateBiggestWidthOnEveryRow(
        listOf(
            AllItemHeaders.NON_INVN.title
        )
    )
    biggestColumnWidths[AllItemHeaders.TAXABLE] = calculateBiggestWidthOnEveryRow(
        listOf(
            AllItemHeaders.TAXABLE.title
        )
    )
    biggestColumnWidths[AllItemHeaders.TAX_ID] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.taxId.toString() } + AllItemHeaders.TAX_ID.title)
    biggestColumnWidths[AllItemHeaders.ITEM_TYPE] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemType.toString() } + AllItemHeaders.ITEM_TYPE.title)

    biggestColumnWidths[AllItemHeaders.SCLASS_ID] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.sClassId.toString() } + AllItemHeaders.SCLASS_ID.title)
    biggestColumnWidths[AllItemHeaders.SUB_CLASS] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.subClass.toString() } + AllItemHeaders.SUB_CLASS.title)
    biggestColumnWidths[AllItemHeaders.CLASS_ID] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.classId.toString() } + AllItemHeaders.CLASS_ID.title)
    biggestColumnWidths[AllItemHeaders.CLASS_NAME] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.className.toString() } + AllItemHeaders.CLASS_NAME.title)
    biggestColumnWidths[AllItemHeaders.DEPT_ID] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.deptId.toString() } + AllItemHeaders.DEPT_ID.title)
    biggestColumnWidths[AllItemHeaders.DEPARTMENT] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.department.toString() } + AllItemHeaders.DEPARTMENT.title)
    biggestColumnWidths[AllItemHeaders.ACTIVE] = calculateBiggestWidthOnEveryRow(
        listOf(
            AllItemHeaders.ACTIVE.title
        )
    )
    biggestColumnWidths[AllItemHeaders.OPEN_PRICE] = calculateBiggestWidthOnEveryRow(
        listOf(
            AllItemHeaders.OPEN_PRICE.title
        )
    )
    biggestColumnWidths[AllItemHeaders.INDEX_ID] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.indexId.toString() } + AllItemHeaders.INDEX_ID.title)

    return biggestColumnWidths
}

private enum class AllItemHeaders(val title: String) {
    ITEM_ID("Item_ID"),
    ITEM_CODE("Item_Code"),
    UPC("UPC"),
    ALU("ALU"),
    NAME("Name"),
    NAME2("Secondary Name"),
    DESCRIPTION("Description"),
    STYLE_ID("Style ID"),
    STYLE_NAME("Style Name"),
    ON_HAND("On Hand"),
    FREE_CARD("Free Card"),
    FREE_CARD_PRICE("Free Card Price"),
    PRICE("Price"),
    QTY("Quantity"),
    ITEM_DISCOUNT("Item Discount"),
    GRID1("Grid1"),
    GRID2("Grid2"),
    GRID3("Grid3"),
    VEND_ID("Vendor_ID"),
    VEND_NAME("Vendor_Name"),
    NON_INVN("Non_Invn"),
    TAXABLE("Taxable"),
    TAX_ID("Tax_ID"),
    ITEM_TYPE("Item Type"),
    SCLASS_ID("Subclass_ID"),
    SUB_CLASS("Subclass"),
    CLASS_ID("Class_ID"),
    CLASS_NAME("Class_Name"),
    DEPT_ID("Department_ID"),
    DEPARTMENT("Department"),
    ACTIVE("Active"),
    OPEN_PRICE("Open_Price"),
    UDF1("UDF1"),
    UDF2("UDF2"),
    UDF3("UDF3"),
    UDF4("UDF4"),
    UDF5("UDF5"),
    UDF6("UDF6"),
    UDF7("UDF7"),
    UDF8("UDF8"),
    UDF9("UDF9"),
    UDF10("UDF10"),
    UDF11("UDF11"),
    UDF12("UDF12"),
    UDF13("UDF13"),
    UDF14("UDF14"),
    UDF15("UDF15"),
    UDF16("UDF16"),
    UDF17("UDF17"),
    UDF18("UDF18"),
    UDF19("UDF19"),
    UDF20("UDF20"),
    INDEX_ID("Index_ID");
}
