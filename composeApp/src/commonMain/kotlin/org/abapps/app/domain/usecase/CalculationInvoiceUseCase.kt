package org.abapps.app.domain.usecase

import org.abapps.app.data.util.RetailSetup
import org.abapps.app.presentation.screens.posInvoiceScreen.Calculations
import org.abapps.app.presentation.screens.posInvoiceScreen.NewInvoiceItemUiState
import org.abapps.app.presentation.screens.transferNewInvoice.TransferNewInvoiceItemUiState
import org.abapps.app.util.roundToDecimals

class CalculationInvoiceUseCase {

    fun calculateItemsPrice(items: List<NewInvoiceItemUiState>): List<NewInvoiceItemUiState> {
        return items.map { item ->
            if ((!RetailSetup.VAT && (!RetailSetup.TAX_EFFECT || !RetailSetup.TAX_EFFECT_WITH_ITEM)) ||
                (!RetailSetup.VAT && (RetailSetup.TAX_EFFECT || RetailSetup.TAX_EFFECT_WITH_ITEM))
            ) {
                val priceWOT = item.orgPrice.roundToDecimals(2) - item.itemDisc
                val taxAmount = if (item.itemDisc == 0f)
                    (item.taxPerc.div(100f) * priceWOT).roundToDecimals(RetailSetup.LEN_DECIMAL)
                else item.taxAmount
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
                val priceWOT =
                    (item.orgPrice / (1 + (item.taxPerc / 100))).roundToDecimals(2) - item.itemDisc
                val taxAmount = if (item.itemDisc == 0f)
                    (item.taxPerc.div(100f) * priceWOT).roundToDecimals(RetailSetup.LEN_DECIMAL)
                else item.taxAmount
                val price = (priceWOT + taxAmount).roundToDecimals(RetailSetup.LEN_DECIMAL)
                val extPrice = (price * item.qty.toFloat()).roundToDecimals(RetailSetup.LEN_DECIMAL)
                item.copy(
                    priceWOT = priceWOT,
                    taxAmount = taxAmount,
                    price = price,
                    extPrice = extPrice,
                )
            } else {
                item
            }
        }
    }

    fun calculateItemDiscount(
        items: List<NewInvoiceItemUiState>,
        calculations: Calculations,
        itemId: Long,
    ): List<NewInvoiceItemUiState> {
        val item = items.filter { it.itemID == itemId }.map { item ->
            if ((!RetailSetup.VAT && (!RetailSetup.TAX_EFFECT || !RetailSetup.TAX_EFFECT_WITH_ITEM)) ||
                (!RetailSetup.VAT && (RetailSetup.TAX_EFFECT || RetailSetup.TAX_EFFECT_WITH_ITEM))
            ) {
                val priceWOT =
                    item.orgPrice.roundToDecimals(2)-  (item.orgPrice.roundToDecimals(2) * (calculations.discountAmount / 100))
                val taxAmount = if (!RetailSetup.TAX_EFFECT || !RetailSetup.TAX_EFFECT_WITH_ITEM)
                    calculations.totalTax
                else (item.taxPerc.div(100f) * priceWOT).roundToDecimals(RetailSetup.LEN_DECIMAL) * (calculations.discountAmount / 100)
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
                val priceWOT =
                    (item.orgPrice / (1 + (item.taxPerc / 100))).roundToDecimals(2) * (calculations.discountAmount / 100)
                val taxAmount = if (!RetailSetup.TAX_EFFECT || !RetailSetup.TAX_EFFECT_WITH_ITEM)
                    calculations.totalTax
                else (item.taxPerc.div(100f) * priceWOT).roundToDecimals(RetailSetup.LEN_DECIMAL) * (calculations.discountAmount / 100)
                val price = (priceWOT + taxAmount).roundToDecimals(RetailSetup.LEN_DECIMAL)
                val extPrice = (price * item.qty.toFloat()).roundToDecimals(RetailSetup.LEN_DECIMAL)
                item.copy(
                    priceWOT = priceWOT,
                    taxAmount = taxAmount,
                    price = price,
                    extPrice = extPrice,
                    itemDisc = priceWOT
                )
            } else {
                item.copy()
            }
        }
        val temp = items.toMutableList()
        temp.remove(items.find { it.itemID == itemId })
        temp.addAll(item)
        return temp
    }


    fun calculateInvoice(
        items: List<NewInvoiceItemUiState>,
        calculations: Calculations
    ): Calculations {
        val subTotal =
            items.sumOf { it.priceWOT.toDouble() * it.qty.toDouble() }.toFloat().roundToDecimals(2)
        val tax = items.sumOf { it.taxAmount.toDouble() * it.qty.toDouble() }
            .toFloat()
            .roundToDecimals(RetailSetup.LEN_DECIMAL)
        val totalTax =
            if (RetailSetup.TAX_EFFECT || RetailSetup.TAX_EFFECT_WITH_ITEM)
                if (calculations.discountAmount == 0f) tax else
                    (tax - (tax * (calculations.discountAmount / 100))).roundToDecimals(RetailSetup.LEN_DECIMAL)
            else tax
        val discountTotal =
            if (RetailSetup.VAT && calculations.discountAmount != 0f)
                    ((calculations.discountAmount / 100) * (calculations.subTotal + calculations.totalTax)).roundToDecimals(3)
            else (subTotal * (calculations.discountAmount / 100)).roundToDecimals(3)
        val netTotal =
            if (RetailSetup.VAT && calculations.discountAmount != 0f) discountTotal.roundToDecimals(
                3
            )
            else (subTotal - discountTotal).roundToDecimals(3)
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

    fun calculateTransfer(
        items: List<TransferNewInvoiceItemUiState>,
        calculations: org.abapps.app.presentation.screens.transferNewInvoice.Calculations
    ): org.abapps.app.presentation.screens.transferNewInvoice.Calculations {
        return calculations.copy(
            totalPrice = items.sumOf { it.price.toDouble() }.toFloat()
                .roundToDecimals(RetailSetup.LEN_DECIMAL),
            totalCost = items.sumOf { it.cost.toDouble() }.toFloat()
                .roundToDecimals(3),
            totalQtyTran = items.sumOf { it.qtyTran.toDouble() }.toFloat()
                .roundToDecimals(3),
            totalQtyOnHand = items.sumOf { it.qtyOH.toDouble() }.toFloat()
                .roundToDecimals(3),
        )
    }
}