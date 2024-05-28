package org.abapps.app.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DiscountDto(
    val discId: Int? = null,
    val code: String? = null,
    val name: String? = null,
    val discType: String? = null,
    val value: Float? = null,
    val manager: Boolean? = null,
    val active: Boolean? = null,
    val createDate: String? = null,
    val modifiedDate: String? = null,
    val userId: Int? = null,
    val scomId: Int? = null,
    val excludeItemsDiscount: Boolean? = null,
    val promoCode: Boolean? = null,
    val promoCodeId: Int? = null
)