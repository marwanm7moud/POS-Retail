package org.abapps.app.presentation.screens.posInvoiceScreen

import androidx.compose.runtime.Immutable
import org.abapps.app.domain.entities.Item
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.presentation.screens.composable.DropDownState

data class NewInvoiceUiState(
    val errorMessage: String = "",
    val errorState: ErrorState? = null,
    val isLoading: Boolean = false,
    val showErrorScreen: Boolean = false,
    val isAddItem: Boolean = false,
    val selectedItemsIndexFromAllItems: List<Int> = emptyList(),
    val selectedItemIndexFromInvoice: Int = -1,
    val invoiceItemList: List<NewInvoiceItemUiState> = emptyList(),
    val allItemsList: List<ItemUiState> = emptyList(),
    val expandedCardStatus: ExpandedCardStatus? = null,
    val stores: List<InvoiceDataState> = emptyList(),
    val selectedStore: InvoiceDataState = InvoiceDataState(),
    val sales: List<InvoiceDataState> = emptyList(),
    val selectedSalePerson: InvoiceDataState = InvoiceDataState(),
    val customers: List<InvoiceDataState> = emptyList(),
    val selectedCustomer: InvoiceDataState = InvoiceDataState(),
    val invoiceTypes: List<InvoiceDataState> = listOf(
        InvoiceDataState(1, "Regular"),
        InvoiceDataState(2, "Return"),
    ),
    val selectedInvoiceType: InvoiceDataState = invoiceTypes.first(),
    val invoiceNumber: Int = 0,
    val cashierName: String = "",
)

@Immutable
data class InvoiceDataState(
    val id: Int = 0,
    val name: String = ""
)

fun InvoiceDataState.toDropDownState(): DropDownState = DropDownState(
    id, name
)

enum class ExpandedCardStatus {
    Items, Brandon
}

data class NewInvoiceItemUiState(
    val itemCode: Int = 132,
    val alu: Long = 654,
    val name: String = "marnasdasdasd",
    val qty: Float = 1f,
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
    val onHand: Float = 51f,
    val freeCard: Boolean = true,
    val freeCardPrice: Int = 10,
    val price: Int = 1000,
    val taxPerc: Int = 20,
    val qty: Float = 1f,
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

fun Item.toUiState(): ItemUiState {
    return ItemUiState(
        itemID = this.itemID,
        itemCode = this.itemCode,
        upc = this.upc,
        alu = this.upc.toLong(),
        name = this.name,
        name2 = this.name2,
        description = this.description,
        styleId = this.styleId,
        styleName = this.styleName,
        onHand = this.onHand,
        freeCard = this.freeCard,
        freeCardPrice = this.freeCardPrice.toInt(),
        price = this.price.toInt(),
        taxPerc = this.taxPerc.toInt(),
        qty = this.qty,
        itemDiscount = this.itemDiscount.toInt(),
        grid1 = this.grid1,
        grid2 = this.grid2,
        grid3 = this.grid3,
        vendId = this.vendId,
        vendName = this.vendName,
        nonInvn = this.nonInvn,
        taxable = this.taxable,
        taxId = this.taxId,
        itemType = this.itemType,
        sClassId = this.sClassId,
        subClass = this.subClass,
        classId = this.classId,
        className = this.className,
        deptId = this.deptId,
        department = this.department,
        active = this.active,
        openPrice = this.openPrice,
        UDF1 = this.UDF1,
        UDF2 = this.UDF2,
        UDF3 = this.UDF3,
        UDF4 = this.UDF4,
        UDF5 = this.UDF5,
        UDF6 = this.UDF6,
        UDF7 = this.UDF7,
        UDF8 = this.UDF8,
        UDF9 = this.UDF9,
        UDF10 = this.UDF10,
        UDF11 = this.UDF11,
        UDF12 = this.UDF12,
        UDF13 = this.UDF13,
        UDF14 = this.UDF14,
        UDF15 = this.UDF15,
        UDF16 = this.UDF16,
        UDF17 = this.UDF17,
        UDF18 = this.UDF18,
        UDF19 = this.UDF19,
        UDF20 = this.UDF20,
        indexId = this.indexId
    )
}

fun ItemUiState.toInvoiceItemUiState(): NewInvoiceItemUiState = NewInvoiceItemUiState(
    itemCode = itemCode,
    alu = alu,
    name = name,
    qty = qty,
    orgPrice = price,
    itemDisc = itemDiscount,
    price = price,
    extPrice = price,
    priceWOT = price,
    taxPerc = taxPerc,
    taxAmount = price,
    itemSerial = 0,
)