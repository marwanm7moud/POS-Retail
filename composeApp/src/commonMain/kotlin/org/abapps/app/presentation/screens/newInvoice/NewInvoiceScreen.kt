package org.abapps.app.presentation.screens.newInvoice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.beepbeep.designSystem.ui.composable.StAppBar
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.presentation.screens.allItems.AllItemScreen
import org.abapps.app.presentation.screens.newInvoice.composables.CalculationsBar
import org.abapps.app.presentation.screens.newInvoice.composables.NewInvoiceItemTable
import org.jetbrains.compose.resources.painterResource
import pos_retail.composeapp.generated.resources.Res
import pos_retail.composeapp.generated.resources.ic_back

class NewInvoiceScreen : Screen {
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val invoiceItemList : List<NewInvoiceItemUiState> by remember { mutableStateOf(listOf(NewInvoiceItemUiState(),NewInvoiceItemUiState(alu = 65551561465116596),NewInvoiceItemUiState(),NewInvoiceItemUiState(),NewInvoiceItemUiState(),NewInvoiceItemUiState(),NewInvoiceItemUiState(),NewInvoiceItemUiState())) }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                StAppBar(onNavigateUp = {
                nav.pop()
            },
                title = "Invoice",
                isBackIconVisible = true,
                painterResource = painterResource(Res.drawable.ic_back),
                actions = {
                    Row(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp)).clickable {
                            nav.push(AllItemScreen())
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
        },
            bottomBar = { CalculationsBar() }
        ) {
            NewInvoiceItemTable(modifier = Modifier.padding(top = it.calculateTopPadding() , bottom = it.calculateBottomPadding()), invoiceItems = invoiceItemList)
        }
    }


}