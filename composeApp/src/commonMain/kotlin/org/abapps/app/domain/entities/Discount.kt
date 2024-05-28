package org.abapps.app.domain.entities

data class Discount(
    val discId: Int,
    val code: String,
    val name: String,
    val discType: String,
    val value: Float,
    val manager: Boolean,
    val active: Boolean,
    val createDate: String,
    val modifiedDate: String,
    val userId: Int,
    val scomId: Int,
    val excludeItemsDiscount: Boolean,
    val promoCode: Boolean,
    val promoCodeId: Int
)