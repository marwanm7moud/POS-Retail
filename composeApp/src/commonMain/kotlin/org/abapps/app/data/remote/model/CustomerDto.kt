package org.abapps.app.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CustomerDto(
    val id: Long? = null,
    val code: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val fullName: String? = null,
    val scomId: Int? = null,
    val storeId: Int? = null,
    val className: String? = null,
    val storeName: String? = null,
    val priceLevel: Int? = null,
    val discId: Int? = null,
    val customerClassId: Int? = null,
    val active: Boolean? = null,
    val reasonDeActive: String? = null,
    val phone: String? = null,
    val phone2: String? = null,
    val phone3: String? = null,
    val address: String? = null,
    val address2: String? = null,
    val email: String? = null,
    val socialId: String? = null,
    val ar: Boolean? = null,
    val creditLimit: Float? = null,
    val createDate: String? = null,
    val modifiedDate: String? = null,
    val userID: Int? = null,
    val eType: String? = null,
    val eIdNumber: String? = null,
    val totalInvoices: Float? = null,
    val returnedInvoices: Int? = null,
    val invoicesCount: Int? = null,
    val countReturned: Int? = null,
)