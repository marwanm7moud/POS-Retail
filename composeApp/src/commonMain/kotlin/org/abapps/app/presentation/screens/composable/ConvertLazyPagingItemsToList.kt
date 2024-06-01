package org.abapps.app.presentation.screens.composable

import androidx.compose.runtime.Composable
import app.cash.paging.compose.LazyPagingItems

@Composable
fun <T : Any> convertLazyPagingItemsToList(lazyPagingItems: LazyPagingItems<T>): List<T> {
    val itemsList = mutableListOf<T>()
    // Iterate through LazyPagingItems and add to the list
    for (index in 0 until lazyPagingItems.itemCount) {
        itemsList.add(lazyPagingItems[index] ?: break)
    }
    return itemsList
}