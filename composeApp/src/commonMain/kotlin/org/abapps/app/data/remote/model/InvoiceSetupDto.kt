package org.abapps.app.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class InvoiceSetupDto(
    val storeId: Int? = null,
    val allawPartialPaymentOfCheck: Boolean? = null,
    val checkPrinter: Int? = null,
    val defaultCust: Int? = null,
    val defaultSeller: Int? = null,
    val closeInvcWhenBalanceZero: Boolean? = null,
    val multiSeller: Boolean? = null,
    val allowInsertItemInTwoLine: Boolean? = null,
    val countCopyReceipt: Int? = null,
    val createDate: String? = null,
    val modifiedDate: String? = null,
    val userId: Int? = null,
    val allawSellWithNegativeOnHand: Boolean? = null,
    val quickPayCash: Boolean? = null,
    val requiredReturnReason: Boolean? = null,
    val setCancelInvcNo0: Boolean? = null,
    val validateReturn: Boolean? = null,
    val noOfDayReturn: Int? = null,
    val returnOnSale: Boolean? = null,
    val returnSamePaymentType: Boolean? = null,
    val returnPaymentType: Int? = null,
    val autoPrintGiftRec: Boolean? = null,
    val firstName: String? = null,
    val name: String? = null
)