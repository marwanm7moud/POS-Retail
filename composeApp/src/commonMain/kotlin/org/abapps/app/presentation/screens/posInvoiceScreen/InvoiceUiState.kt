package org.abapps.app.presentation.screens.posInvoiceScreen

import org.abapps.app.presentation.base.ErrorState

data class NewInvoiceUiState(
    val errorMessage: String = "",
    val errorState: ErrorState? = null,
    val isLoading: Boolean = false,
    val showErrorScreen: Boolean = false,
    val isAddItem: Boolean = false,
    val selectedItemsIndexFromAllItems: List<Int> = emptyList(),
    val selectedItemIndexFromInvoice: Int = -1,
    val invoiceItemList: List<NewInvoiceItemUiState> = emptyList(),
    val allItemsList: List<ItemUiState> = listOf(
        ItemUiState(),
        ItemUiState(),
        ItemUiState(),
        ItemUiState(),
        ItemUiState()
    ),
    val expandedCardStatus:ExpandedCardStatus? = null
)

enum class ExpandedCardStatus{
    Items,
    Brandon
}

data class NewInvoiceItemUiState(
    val itemCode: Int = 132,
    val alu: Long = 654,
    val name: String = "marnasdasdasd",
    val qty: Int = 1,
    val orgPrice: Int = 20,
    val itemDisc: Int = 20,
    val price: Int = 20,
    val extPrice: Int = 20,
    val priceWOT: Int = 20,
    val taxPerc: Int = 20,
    val taxAmount: Int = 20,
    val itemSerial: Int = 20,
)

data class ItemUiState(
    val itemID: Long = 10000003869,
    val itemCode: Int = 3465,
    val upc: Int = 654,
    val alu: Long = 654,
    val name: String = "طقم ملايه بولي قطن",
    val name2: String = "uhukhkjhk",
    val description: String = " خسعيابستي بمتس بهتسي بمتسيمنب تسيمن بتمينتب منسيت بمنيست بمنتسيم بنتيسمن تبمن",
    val styleId: Int = 0,
    val styleName: String = "احمر",
    val onHand: Int = 51,
    val freeCard: Boolean = true,
    val freeCardPrice: Int = 10,
    val price: Int = 1000,
    val taxPerc: Int = 20,
    val qty: Int = 1,
    val itemDiscount: Int = 1,
    val grid1: String = "",
    val grid2: String = "",
    val grid3: String = "",
    val vendId: Int = 231654,
    val vendName: String = "",
    val nonInvn: Boolean = false,
    val taxable: Boolean = true,
    val taxId: Int = 151321,
    val itemType: String = "",
    val sClassId: Int = 151321,
    val subClass: String = "",
    val classId: Int = 151321,
    val className: String = "",
    val deptId: Int = 151321,
    val department: String = "",
    val active: Boolean = true,
    val openPrice: Boolean = false,
    val UDF1: String = "",
    val UDF2: String = "",
    val UDF3: String = "",
    val UDF4: String = "",
    val UDF5: String = "",
    val UDF6: String = "",
    val UDF7: String = "",
    val UDF8: String = "",
    val UDF9: String = "",
    val UDF10: String = "",
    val UDF11: String = "",
    val UDF12: String = "",
    val UDF13: String = "",
    val UDF14: String = "",
    val UDF15: String = "",
    val UDF16: String = "",
    val UDF17: String = "",
    val UDF18: String = "",
    val UDF19: String = "",
    val UDF20: String = "",
    val indexId: Int = 151321,
)

fun ItemUiState.toInvoiceItemUiState():NewInvoiceItemUiState = NewInvoiceItemUiState(
    itemCode = itemCode,
    alu = alu,
    name = name,
    qty = qty,
    orgPrice = price,
    itemDisc = itemDiscount,
    price = price, //todo
    extPrice = price,//todo
    priceWOT = price,//todo
    taxPerc = taxPerc,//todo
    taxAmount = price,//todo
    itemSerial = 0 ,//todo

)
