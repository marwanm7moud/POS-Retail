package org.abapps.app.presentation.screens.allinvoices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.beepbeep.designSystem.ui.composable.StAppBar
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.presentation.screens.allinvoices.composables.InvoicesItemTable
import org.abapps.app.presentation.screens.invoiceScreen.InvoiceScreen
import org.abapps.app.presentation.util.EventHandler
import org.abapps.app.util.getScreenModel

class AllInvoicesScreen : Screen {

    @Composable
    override fun Content() {

        val screenModel = getScreenModel<AllInvoicesScreenModel>()
        val state by screenModel.state.collectAsState()

        EventHandler(screenModel.effect){effect, navigator ->
            when(effect){
                is AllInvoicesUiEffect.NavigateToNewInvoice-> {
                    navigator.push(InvoiceScreen())
                }
                else -> {}
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Transparent,
            topBar = {
                StAppBar(
                    title = "Invoices",
                    isBackIconVisible = false,
                )
            },
            floatingActionButton = {
                Box {
                    FloatingActionButton(
                        onClick = screenModel::onClickFloatActionButton,
                        containerColor = Theme.colors.primary
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = null,
                                tint = Theme.colors.contentPrimary
                            )
                            Text(
                                "New Invoice",
                                style = Theme.typography.title.copy(color = Color.White)
                            )
                        }
                    }
                }
            })
        {
            InvoicesItemTable(
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                invoiceItems = state.invoicesList,
                selectedItemIndex = state.selectedInvoiceIndex,
                onClickItem = screenModel::onClickItem,
                onClickItemEdit = {},
                onClickItemDelete = screenModel::onClickItemDelete,
                onClickItemCopy = {}
            )
        }
    }
}