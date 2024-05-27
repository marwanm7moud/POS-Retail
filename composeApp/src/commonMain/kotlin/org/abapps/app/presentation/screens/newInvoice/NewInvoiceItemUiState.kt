package org.abapps.app.presentation.screens.newInvoice


data class NewInvoiceItemUiState(
    val itemCode: Int = 132,
    val alu: Long = 654,
    val name: String = "marnasdasdasd",
    val qty: Int = 1,
    val orgPrice: Int = 20,
    val itemDisc: Int = 20,
    val price: Int = 20,
    val extPrice: Int = 20,
    val priceWOT: Int = 20,
    val taxPerc: Int = 20,
    val taxAmount: Int = 20,
    val itemSerial: Int = 20,
)
