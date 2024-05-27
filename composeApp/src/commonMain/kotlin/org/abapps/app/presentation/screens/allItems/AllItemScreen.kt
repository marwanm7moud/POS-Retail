package org.abapps.app.presentation.screens.allItems

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BadgedBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
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
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.presentation.screens.allItems.composables.AllItemTable
import org.jetbrains.compose.resources.painterResource
import pos_retail.composeapp.generated.resources.Res
import pos_retail.composeapp.generated.resources.ic_back

class AllItemScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val nav = LocalNavigator.currentOrThrow
        val itemList: List<ItemUiState> by remember {
            mutableStateOf(
                listOf(
                    ItemUiState(),
                    ItemUiState(),
                    ItemUiState(),
                    ItemUiState(),
                    ItemUiState()
                )
            )
        }

        var selectedItemIndex: List<Int> by remember { mutableStateOf(listOf()) }


        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                StAppBar(onNavigateUp = {
                    nav.pop()
                },
                    title = "All Items",
                    isBackIconVisible = true,
                    painterResource = painterResource(Res.drawable.ic_back),
                    actions = {
                        Row(
                            modifier = Modifier.clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    //todo
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
                                "Create Item",
                                style = Theme.typography.title.copy(color = Color.White)
                            )
                        }
                    })
            },
            floatingActionButton = {
                Box {
                    FloatingActionButton(
                        onClick = {},
                        containerColor = Theme.colors.primary
                    ) {
                        Icon(
                            Icons.Filled.Done,
                            contentDescription = null,
                            tint = Theme.colors.contentPrimary
                        )
                    }
                    BadgedBox(
                        {
                            Box(
                                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(
                                            color = Theme.colors.contentPrimary,
                                            shape = CircleShape
                                        ).align(Alignment.Center).padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        selectedItemIndex.size.toString(),
                                        style = Theme.typography.title,
                                        modifier = Modifier.fillMaxSize()
                                            .align(Alignment.Center),
                                        textAlign = TextAlign.Center,
                                        color = Theme.colors.surface
                                    )
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.TopCenter).padding(start = 8.dp)
                    ) {
                    }
                }
            },
        ) {
            AllItemTable(
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                invoiceItems = itemList,
                selectedItemIndex = selectedItemIndex,
                onClickItem = { index ->
                    if (selectedItemIndex.contains(index))
                        selectedItemIndex -= index
                    else
                        selectedItemIndex += index
                })
        }
    }
}