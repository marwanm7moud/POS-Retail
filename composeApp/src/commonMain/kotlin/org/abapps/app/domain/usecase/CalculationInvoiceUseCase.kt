package org.abapps.app.domain.usecase

import org.abapps.app.data.util.RetailSetup
import org.abapps.app.presentation.screens.posInvoiceScreen.Calculations
import org.abapps.app.presentation.screens.posInvoiceScreen.NewInvoiceItemUiState
import org.abapps.app.util.roundToDecimals

class CalculationInvoiceUseCase {

    fun calculateItemsPrice(items: List<NewInvoiceItemUiState>): List<NewInvoiceItemUiState> {
        return items.map { item ->
            if ((!RetailSetup.VAT && (!RetailSetup.TAX_EFFECT || !RetailSetup.TAX_EFFECT_WITH_ITEM)) ||
                (!RetailSetup.VAT && (RetailSetup.TAX_EFFECT || RetailSetup.TAX_EFFECT_WITH_ITEM))
            ) {
                val priceWOT = item.orgPrice.roundToDecimals(2)
                val taxAmount =
                    (item.taxPerc.div(100f) * priceWOT).roundToDecimals(RetailSetup.LEN_DECIMAL)
                val price = (priceWOT + taxAmount).roundToDecimals(RetailSetup.LEN_DECIMAL)
                val extPrice = (price * item.qty.toFloat()).roundToDecimals(RetailSetup.LEN_DECIMAL)
                item.copy(
                    priceWOT = priceWOT,
                    taxAmount = taxAmount,
                    price = price,
                    extPrice = extPrice,
                )
            } else if ((RetailSetup.VAT && (!RetailSetup.TAX_EFFECT || !RetailSetup.TAX_EFFECT_WITH_ITEM)) ||
                (RetailSetup.VAT && (RetailSetup.TAX_EFFECT || RetailSetup.TAX_EFFECT_WITH_ITEM))
            ) {
                val priceWOT = (item.orgPrice / (1 + (item.taxPerc / 100))).roundToDecimals(2)
                val taxAmount =
                    (item.taxPerc.div(100f) * priceWOT).roundToDecimals(RetailSetup.LEN_DECIMAL)
                val price = (priceWOT + taxAmount).roundToDecimals(RetailSetup.LEN_DECIMAL)
                val extPrice = (price * item.qty.toFloat()).roundToDecimals(RetailSetup.LEN_DECIMAL)
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
        val subTotal = items.sumOf { it.priceWOT.toDouble() }.toFloat().roundToDecimals(2)
        val totalTax = items.sumOf { it.taxAmount.toDouble() }.toFloat()
            .roundToDecimals(RetailSetup.LEN_DECIMAL)
        val discountTotal =
            (subTotal * (calculations.discountAmount / 100)).roundToDecimals(RetailSetup.LEN_DECIMAL - 1)
        val netTotal =
            if (RetailSetup.VAT && calculations.discountAmount != 0f) discountTotal.roundToDecimals(
                RetailSetup.LEN_DECIMAL - 1
            )
            else (subTotal - discountTotal).roundToDecimals(RetailSetup.LEN_DECIMAL - 1)
        val amount =
            if (!RetailSetup.VAT) (subTotal + totalTax - discountTotal + calculations.fee).roundToDecimals(
                RetailSetup.LEN_DECIMAL
            )
            else if (!RetailSetup.TAX_EFFECT || !RetailSetup.TAX_EFFECT_WITH_ITEM) (netTotal + totalTax + calculations.fee).roundToDecimals(
                RetailSetup.LEN_DECIMAL
            )
            else (netTotal + totalTax + calculations.fee).roundToDecimals(RetailSetup.LEN_DECIMAL)
        val remaining = (amount - calculations.totalPaid).roundToDecimals(RetailSetup.LEN_DECIMAL)
        return calculations.copy(
            subTotal = subTotal,
            totalTax = totalTax,
            netTotal = netTotal,
            amount = amount,
            remaining = remaining
        )
    }
}