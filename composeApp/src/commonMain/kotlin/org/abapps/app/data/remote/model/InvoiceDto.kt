package org.abapps.app.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class InvoiceDto(
    val invcId: Int? = null,
    val invcNo: Int? = null,
    val invcType: String? = null,
    val invcDate: String? = null,
    val status: String? = null,
    val reverse: Int? = null,
    val comment: String? = null,
    val cancelled: Boolean? = null,
    val hold: Boolean? = null,
    val post: Boolean? = null,
    val postedDate: String? = null,
    val custId: Int? = null,
    val cashierId: Int? = null,
    val sellerId: Int? = null,
    val createDate: String? = null,
    val modifiedDate: String? = null,
    val userId: Int? = null,
    val scomId: Int? = null,
    val storeId: Int? = null,
    val ws: Int? = null,
    val numPrint: Int? = null,
    val pointId: Int? = null,
    val soId: Int? = null,
    val sourceType: String? = null,
    val voidReasonId: Int? = null,
    val eUuid: String? = null,
    val ePreviousUuid: String? = null,
    val eSubmissionId: String? = null,
    val eStatus: String? = null
)