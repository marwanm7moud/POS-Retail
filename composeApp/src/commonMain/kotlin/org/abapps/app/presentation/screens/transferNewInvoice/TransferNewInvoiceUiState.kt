package org.abapps.app.presentation.screens.transferNewInvoice

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.presentation.screens.posInvoiceScreen.Calculations
import org.abapps.app.presentation.screens.posInvoiceScreen.ItemUiState

data class TransferNewInvoiceUiState(
    val errorMessage: String = "",
    val errorState: ErrorState? = null,
    val isLoading: Boolean = false,
    val showErrorScreen: Boolean = false,
    val isAddItem: Boolean = false,
    val errorDialogueIsVisible: Boolean = false,
    val selectedItemsIndexFromAllItems: List<Int> = emptyList(),
    val selectedItemIndexFromInvoice: Int = -1,
    val allItemsList: Flow<PagingData<ItemUiState>> = emptyFlow(),

    val invoiceItemList: List<TransferNewInvoiceItemUiState> = listOf(
        TransferNewInvoiceItemUiState(),
        TransferNewInvoiceItemUiState(),
        TransferNewInvoiceItemUiState(),
        TransferNewInvoiceItemUiState(),
    ),
    val expandedCardStatus: ExpandedCardStatus? = null,
    val calculations: Calculations = Calculations(),
    val calculationItem: Calculations = calculations.copy(discountAmount = 0f),
)
fun ItemUiState.toTransferNewInvoiceItemUiState(): TransferNewInvoiceItemUiState = TransferNewInvoiceItemUiState(
    itemID = itemID,
    itemCode = itemCode,
    alu = alu.toLong(),
    name = name,
    qtyOH = onHand,
    comment = "",
    qtyTran = "1f",
    cost = cost,
    price = price,
    UDF11 = UDF11,
    UDF12 = UDF12
)

enum class ExpandedCardStatus {
    Items,
    Brandon
}

data class TransferNewInvoiceItemUiState(
    val itemID:Long = 1643,
    val itemCode: Int = 132,
    val alu: Long = 654,
    val name: String = "marnasdasdasd",
    val qtyOH: Float = 1f,
    var qtyTran: String = "1f",
    val comment: String = "marnasdasdasd",
    val cost: Float = 1f,
    val price: Float = 1f,
    val UDF11: String = "",
    val UDF12: String = ""
)

//data class ItemUiState(
//    val itemID: Long = 10000003869,
//    val itemCode: Int = 3465,
//    val upc: Int = 654,
//    val alu: Long = 654,
//    val name: String = "طقم ملايه بولي قطن",
//    val name2: String = "uhukhkjhk",
//    val description: String = " خسعيابستي بمتس بهتسي بمتسيمنب تسيمن بتمينتب منسيت بمنيست بمنتسيم بنتيسمن تبمن",
//    val styleId: Int = 0,
//    val styleName: String = "احمر",
//    val onHand: Int = 51,
//    val freeCard: Boolean = true,
//    val freeCardPrice: Int = 10,
//    val price: Int = 1000,
//    val taxPerc: Int = 20,
//    val qty: Int = 1,
//    val itemDiscount: Int = 1,
//    val grid1: String = "",
//    val grid2: String = "",
//    val grid3: String = "",
//    val vendId: Int = 231654,
//    val vendName: String = "",
//    val nonInvn: Boolean = false,
//    val taxable: Boolean = true,
//    val taxId: Int = 151321,
//    val itemType: String = "",
//    val sClassId: Int = 151321,
//    val subClass: String = "",
//    val classId: Int = 151321,
//    val className: String = "",
//    val deptId: Int = 151321,
//    val department: String = "",
//    val active: Boolean = true,
//    val openPrice: Boolean = false,
//    val UDF1: String = "",
//    val UDF2: String = "",
//    val UDF3: String = "",
//    val UDF4: String = "",
//    val UDF5: String = "",
//    val UDF6: String = "",
//    val UDF7: String = "",
//    val UDF8: String = "",
//    val UDF9: String = "",
//    val UDF10: String = "",
//    val UDF11: String = "",
//    val UDF12: String = "",
//    val UDF13: String = "",
//    val UDF14: String = "",
//    val UDF15: String = "",
//    val UDF16: String = "",
//    val UDF17: String = "",
//    val UDF18: String = "",
//    val UDF19: String = "",
//    val UDF20: String = "",
//    val indexId: Int = 151321,
//)
//
//fun ItemUiState.toInvoiceItemUiState():NewInvoiceItemUiState = NewInvoiceItemUiState(
//    itemCode = itemCode,
//    alu = alu,
//    name = name,
//    qty = qty,
//    orgPrice = price,
//    itemDisc = itemDiscount,
//    price = price, //todo
//    extPrice = price,//todo
//    priceWOT = price,//todo
//    taxPerc = taxPerc,//todo
//    taxAmount = price,//todo
//    itemSerial = 0 ,//todo
//
//)
