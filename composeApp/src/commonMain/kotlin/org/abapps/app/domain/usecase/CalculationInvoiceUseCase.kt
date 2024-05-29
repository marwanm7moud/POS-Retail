package org.abapps.app.domain.usecase

import org.abapps.app.data.util.RetailSetup
import org.abapps.app.presentation.screens.posInvoiceScreen.Calculations
import org.abapps.app.presentation.screens.posInvoiceScreen.NewInvoiceItemUiState

class CalculationInvoiceUseCase {

    fun calculateItemsPrice(items: List<NewInvoiceItemUiState>): List<NewInvoiceItemUiState> {
        return items.map { item ->
            if ((!RetailSetup.VAT && (!RetailSetup.TAX_EFFECT && !RetailSetup.TAX_EFFECT_WITH_ITEM)) ||
                (!RetailSetup.VAT && (RetailSetup.TAX_EFFECT && RetailSetup.TAX_EFFECT_WITH_ITEM))
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
            } else if ((RetailSetup.VAT && (!RetailSetup.TAX_EFFECT && !RetailSetup.TAX_EFFECT_WITH_ITEM)) ||
                (RetailSetup.VAT && (RetailSetup.TAX_EFFECT && RetailSetup.TAX_EFFECT_WITH_ITEM))
            ) {
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
            } else {
                item.copy()
            }
        }
    }

    fun calculateInvoice(
        items: List<NewInvoiceItemUiState>,
        calculations: Calculations
    ): Calculations {
        val subTotal = items.sumOf { it.priceWOT.toDouble() }.toFloat()
        val totalTax = items.sumOf { it.taxAmount.toDouble() }.toFloat()
        val netTotal = subTotal + totalTax
        val remaining = netTotal - calculations.totalPaid
        return calculations.copy(
            subTotal = subTotal,
            totalTax = totalTax,
            netTotal = netTotal,
            amount = netTotal,
            remaining = remaining
        )
    }
}