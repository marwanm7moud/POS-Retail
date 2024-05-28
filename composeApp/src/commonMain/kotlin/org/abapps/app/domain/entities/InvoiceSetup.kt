package org.abapps.app.domain.entities

data class InvoiceSetup(
    val storeId: Int,
    val allawPartialPaymentOfCheck: Boolean,
    val checkPrinter: Int,
    val defaultCust: Long,
    val defaultSeller: Int,
    val closeInvcWhenBalanceZero: Boolean,
    val multiSeller: Boolean,
    val allowInsertItemInTwoLine: Boolean,
    val countCopyReceipt: Int,
    val createDate: String,
    val modifiedDate: String,
    val userId: Int,
    val allawSellWithNegativeOnHand: Boolean,
    val quickPayCash: Boolean,
    val requiredReturnReason: Boolean,
    val setCancelInvcNo0: Boolean,
    val validateReturn: Boolean,
    val noOfDayReturn: Int,
    val returnOnSale: Boolean,
    val returnSamePaymentType: Boolean,
    val returnPaymentType: Int,
    val autoPrintGiftRec: Boolean,
    val firstName: String,
    val name: String
)