package org.abapps.app.presentation.screens.newInvoice.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.abapps.app.domain.entities.Item

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemsTable(data: List<Item>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(12.dp))
    )
    {
        stickyHeader { TableHeader() }
        items(data.size) { index ->
            TableRowItem(data[index], index + 1)
            Spacer(Modifier.fillMaxWidth().background(Color.LightGray).height(0.5.dp))
        }
    }
}

@Composable
fun TableRowItem(item: Item, itemNum: Int) {
    Row(modifier = Modifier.fillMaxSize().padding(vertical = 4.dp)) {
        itemBox(
            itemNum.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(0.5f)
        )
        itemBox(
            item.itemCode.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.alu.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.name.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.qty.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.orgPrice.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.itemDisc.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.price.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.extPrice.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.priceWOT.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.taxPerc.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.taxAmount.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
        itemBox(
            item.itemSerial.toString(),
            modifier = Modifier.defaultMinSize(minWidth = 250.dp).weight(1f)
        )
    }
}

@Composable
fun TableHeader() {
    Row(
        modifier = Modifier.background(color = Color.LightGray).padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemBox("No.", modifier = Modifier.weight(0.5f) , textColor = Color.Gray)
        itemBox("Item Code", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Alu", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Name", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Qty", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("OrgPrice", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("ItemDisc", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Price", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("ExtPrice", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("PriceWOT", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("TaxPerc", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("TaxAmount", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("ItemSerial", modifier = Modifier.weight(1f), textColor = Color.Gray)
    }
}

@Composable
fun itemBox(content: String, modifier: Modifier = Modifier, textColor: Color = Color.White) {
    Box(
        modifier = modifier
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = content, color = textColor)
    }
}