package org.abapps.app.presentation.screens.newInvoice.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.abapps.app.domain.entities.InvoiceItem
import org.abapps.app.util.calculateBiggestWidthOnEveryRow

@Composable
fun ItemsTable(invoiceItems: List<InvoiceItem>, modifier: Modifier) {
    val headers = Headers.entries.toTypedArray()
    val biggestColumnWidths = calculateBiggestWidths(invoiceItems)
    var selectedItemIndex by remember { mutableStateOf(-1) }


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
                   content =  header.title,
                    modifier = Modifier
                        .padding(4.dp)
                        .width(with(LocalDensity.current) {
                            (biggestColumnWidths[header] ?: 0).toDp()
                        })
                )
            }
        }
        LazyColumn {
            items(invoiceItems.size) { index ->
                val item = invoiceItems[index]
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.background(
                        color = if (selectedItemIndex == index) Color(
                            0xFF383a3d
                        ) else Color.Gray
                    ).clickable { selectedItemIndex = index }.padding(vertical = 4.dp)
                ) {
                    headers.forEach { header ->
                        val content = when (header) {
                            Headers.ItemCode -> item.itemCode.toString()
                            Headers.Alu -> item.alu.toString()
                            Headers.Name -> item.name
                            Headers.Qty -> item.qty.toString()
                            Headers.OrgPrice -> item.orgPrice.toString()
                            Headers.ItemDisc -> item.itemDisc.toString()
                            Headers.Price -> item.price.toString()
                            Headers.ExtPrice -> item.extPrice.toString()
                            Headers.PriceWOT -> item.priceWOT.toString()
                            Headers.TaxPercentage -> item.taxPerc.toString()
                            Headers.TaxAmount -> item.taxAmount.toString()
                            Headers.ItemSerial -> item.itemSerial.toString()
                            Headers.Number -> index.toString()
                        }
                        itemBox(
                            content =  content,
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
        }
    }
}

@Composable
fun calculateBiggestWidths(invoiceItems: List<InvoiceItem>): SnapshotStateMap<Headers, Int> {
    val biggestColumnWidths = remember { mutableStateMapOf<Headers, Int>() }
    biggestColumnWidths[Headers.ItemCode] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemCode.toString() } + Headers.ItemCode.title)
    biggestColumnWidths[Headers.Number] =
        calculateBiggestWidthOnEveryRow(listOf(Headers.Number.title, "19"))
    biggestColumnWidths[Headers.Alu] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.alu.toString() } + Headers.Alu.title)
    biggestColumnWidths[Headers.Name] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.name.toString() } + Headers.Name.title)
    biggestColumnWidths[Headers.Qty] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.qty.toString() } + Headers.Qty.title)
    biggestColumnWidths[Headers.OrgPrice] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.orgPrice.toString() } + Headers.OrgPrice.title)
    biggestColumnWidths[Headers.ItemDisc] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemDisc.toString() } + Headers.ItemDisc.title)
    biggestColumnWidths[Headers.Price] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.price.toString() } + Headers.Price.title)
    biggestColumnWidths[Headers.ExtPrice] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.extPrice.toString() } + Headers.ExtPrice.title)
    biggestColumnWidths[Headers.PriceWOT] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.priceWOT.toString() } + Headers.PriceWOT.title)
    biggestColumnWidths[Headers.TaxPercentage] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.taxPerc.toString() } + Headers.TaxPercentage.title)
    biggestColumnWidths[Headers.TaxAmount] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.taxAmount.toString() } + Headers.TaxAmount.title)
    biggestColumnWidths[Headers.ItemSerial] =
        calculateBiggestWidthOnEveryRow(invoiceItems.map { it.itemSerial.toString() } + Headers.ItemSerial.title)
    return biggestColumnWidths
}


@Composable
fun itemBox(content: String, modifier: Modifier = Modifier, textColor: Color = Color.White) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = content,
            color = textColor,
            maxLines = 1,
            textAlign = TextAlign.Center,
        )
    }
}


enum class Headers(val title: String) {
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