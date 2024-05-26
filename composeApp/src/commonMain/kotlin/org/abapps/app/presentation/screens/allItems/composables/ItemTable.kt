package org.abapps.app.presentation.screens.allItems.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.abapps.app.domain.entities.Item
import org.abapps.app.presentation.screens.newInvoice.composables.itemBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemTable(
    modifier: Modifier,
    items: List<Item>,
    selectedItemIndex: List<Int>,
    onClickItem: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .defaultMinSize(minHeight = 120.dp)
            .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(12.dp))
    )
    {
        stickyHeader {
            ItemsHeader()
        }
        items(items.size) { index ->
            ItemRow(items[index], selected = selectedItemIndex.contains(index), onClickItem = { onClickItem(index) })
            Spacer(Modifier.fillMaxWidth().background(Color.LightGray).height(0.5.dp))
        }
    }
}

@Composable
private fun ItemRow(item: Item, selected: Boolean, onClickItem: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize()
            .background(color = if (selected) Color(0xFF383a3d) else Color.Gray).clickable { onClickItem.invoke() }
            .padding(vertical = 4.dp)) {
        itemBox(
            item.itemID.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.itemCode.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.upc.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.alu.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.name.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.name2.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.description.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.styleId.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.styleName.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.onHand.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.freeCard.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.freeCardPrice.toString(),
            modifier = Modifier.weight(1f)
        )
        itemBox(
            item.price.toString(),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ItemsHeader() {
    Row(
        modifier = Modifier.background(color = Color.LightGray).padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemBox("Item ID", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Item Code", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("UPC", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Alu", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Name", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Name2", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Description", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Style ID", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Style Name", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("On Hand", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Free Card", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Free Card Price", modifier = Modifier.weight(1f), textColor = Color.Gray)
        itemBox("Price", modifier = Modifier.weight(1f), textColor = Color.Gray)
    }
}
