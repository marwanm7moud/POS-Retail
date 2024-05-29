package org.abapps.app.domain.usecase

import org.abapps.app.data.util.RetailSetup
import org.abapps.app.presentation.screens.posInvoiceScreen.NewInvoiceItemUiState

class CalculationInvoiceUseCase {

    fun calculateItemsPrice(items: List<NewInvoiceItemUiState>): List<NewInvoiceItemUiState> {
        return items.map { item ->
            if (!RetailSetup.VAT && ((!RetailSetup.TAX_EFFECT && !RetailSetup.TAX_EFFECT_WITH_ITEM))
                || (RetailSetup.TAX_EFFECT && RetailSetup.TAX_EFFECT_WITH_ITEM)
            ) {
                val priceWOT = item.orgPrice
                val taxAmount = item.taxPerc.div(100f) * priceWOT
                val price = priceWOT + taxAmount
                val extPrice = price * item.qty.toFloat()
                item.copy(
                    priceWOT = priceWOT,
                    taxAmount = taxAmount,
                    price = price,
                    extPrice = extPrice,
                )
            } else {
                val priceWOT = item.orgPrice / (1 + (item.taxPerc / 100))
                val taxAmount = item.taxPerc.div(100f) * priceWOT
                val price = priceWOT + taxAmount
                val extPrice = price * item.qty.toFloat()
                item.copy(
                    priceWOT = priceWOT,
                    taxAmount = taxAmount,
                    price = price,
                    extPrice = extPrice,
                )
            }
        }
    }
}