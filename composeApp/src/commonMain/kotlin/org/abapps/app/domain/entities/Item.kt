package org.abapps.app.domain.entities

data class Item(
    val itemCode: Int = 132,
    val alu: Int = 654,
    val name: String = "marn",
    val qty: Int = 2,
    val orgPrice: Int = 20,
    val itemDisc: Int = 20,
    val price: Int = 20,
    val extPrice: Int = 20,
    val priceWOT: Int = 20,
    val taxPerc: Int = 20,
    val taxAmount: Int = 20,
    val itemSerial: Int = 20,
)