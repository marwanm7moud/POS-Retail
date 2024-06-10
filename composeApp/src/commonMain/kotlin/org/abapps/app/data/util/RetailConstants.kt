package org.abapps.app.data.util

import kotlinx.coroutines.flow.MutableStateFlow
import org.abapps.app.util.LanguageCode

object AppLanguage {
    val code: MutableStateFlow<String> = MutableStateFlow(LanguageCode.EN.value)
}

object RetailSetup {
    var STORE_ID = 1
    var SUB_COMPANY_ID = 1
    var DEFAULT_CUSTOMER_ID = 0L
    var DEFAULT_SALES_ID = 0
    var IS_MAIN_STORE = false
    var USER_ID = 1035
    var CASHIER_NAME = ""
    var PRICE_LVL_ID = 0
    var TOKEN = ""
    var WORK_STATION_ID = 0
    var USER_LANGUAGE = LanguageCode.EN.value
    var DEFAULT_LANGUAGE = LanguageCode.EN.value
    var TAX_EFFECT_WITH_ITEM = false
    var FIFO = false
    var AVERAGE = false
    var LAST_COST = false
    var LIFO = false
    var ROUND_PRICE = 0f
    var TAX_EFFECT = false
    var VAT = false
    var LEN_DECIMAL = 0
    var NUMBER_OF_DAY_RETURN = 0
    var ALLOW_NEGATIVE_QTY = false
}