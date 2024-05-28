package org.abapps.app.domain.entities

data class SubCompanySetup(
    val sComId: Int,
    val vat: Boolean,
    val lenDecimal: Int,
    val createDate: String,
    val modifiedDate: String,
    val userId: Int,
    val calcDiscItemOnPriceWot: Boolean,
    val calcCostFifo: Boolean,
    val calcCostAverage: Boolean,
    val calcCostLastCost: Boolean,
    val calcCostLifo: Boolean,
    val roundPrice: Float,
    val calcDiscountBeforeTax: Boolean,
)