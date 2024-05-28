package org.abapps.app.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SubCompanySetupDto(
    val sComId: Int? = null,
    val vat: Boolean? = null,
    val lenDecimal: Int? = null,
    val createDate: String? = null,
    val modifiedDate: String? = null,
    val userId: Int? = null,
    val calcDiscItemOnPriceWot: Boolean? = null,
    val calcCostFifo: Boolean? = null,
    val calcCostAverage: Boolean? = null,
    val calcCostLastCost: Boolean? = null,
    val calcCostLifo: Boolean? = null,
    val roundPrice: Float? = null,
    val calcDiscountBeforeTax: Boolean? = null,
)