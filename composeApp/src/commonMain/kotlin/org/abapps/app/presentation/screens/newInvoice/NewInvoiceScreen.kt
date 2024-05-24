package org.abapps.app.presentation.screens.newInvoice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.beepbeep.designSystem.ui.composable.StAppBar
import com.beepbeep.designSystem.ui.composable.StOutlinedButton
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.domain.entities.Item
import org.abapps.app.presentation.screens.newInvoice.composables.ItemsTable
import org.jetbrains.compose.resources.painterResource
import pos_retail.composeapp.generated.resources.Res
import pos_retail.composeapp.generated.resources.ic_back

class NewInvoiceScreen : Screen {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        var itemList by remember { mutableStateOf(listOf(Item(), Item(), Item(), Item(), Item())) }
        Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Transparent, topBar = {
            StAppBar(onNavigateUp = {
                nav.pop()
            },
                title = "Invoice",
                isBackIconVisible = true,
                painterResource = painterResource(Res.drawable.ic_back),
                actions = {
                    Row(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable {
                            itemList += Item()
                            println(itemList.size)
                        }.padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = null,
                            tint = Theme.colors.contentPrimary
                        )
                        Text(
                            "Add Item", style = Theme.typography.title.copy(color = Color.White)
                        )
                    }
                })
        }, bottomBar = {
            CalculationsBar()
        }) {
            ItemsTable(
                modifier = Modifier.padding(top = it.calculateTopPadding()), data = itemList
            )
        }
    }


}

@Composable
fun CalculationsBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(Color(0xFF0202020))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                CalculationItem("Sub Total", "1000")
                CalculationItem("Total Tax", "140")
                Spacer(modifier = Modifier.height(16.dp))
                CalculationItem("Net Total", "1000")
                CalculationItem("Fee", "0")
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                CalculationItem("Amount", "1140")
                CalculationItem("Total Paid", "0")
                CalculationItem("Remaining", "1140")
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.border(
                    BorderStroke(0.5.dp, Color.LightGray),
                    RoundedCornerShape(12.dp)
                ).padding(16.dp)
            ) {
                CalculationItem("Taken", "1140")
                CalculationItem("Given", "0")
            }
            StOutlinedButton(
                title = "Update",
                onClick = {},
                modifier = Modifier
            )
        }
    }
}

@Composable
fun CalculationItem(title: String, calculatedNumber: String) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = Color.White, modifier = Modifier.width(80.dp))
        Text(
            text = calculatedNumber,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(120.dp)
                .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(12.dp))
                .padding(vertical = 8.dp)
        )
    }
}