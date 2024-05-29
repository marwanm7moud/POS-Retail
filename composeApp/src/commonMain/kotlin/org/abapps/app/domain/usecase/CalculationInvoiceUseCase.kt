package org.abapps.app.domain.usecase

import org.abapps.app.presentation.screens.posInvoiceScreen.NewInvoiceItemUiState

class CalculationInvoiceUseCase {

    fun calculateItemsPrice(items: List<NewInvoiceItemUiState>): List<NewInvoiceItemUiState> {
        return items.map { item ->
            item.copy(extPrice = item.price * item.qty.toFloat())
        }
    }
}