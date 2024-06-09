package org.abapps.app.presentation.screens.posInvoiceScreen

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import org.abapps.app.data.util.RetailSetup
import org.abapps.app.domain.entities.Customer
import org.abapps.app.domain.entities.Item
import org.abapps.app.domain.entities.Store
import org.abapps.app.domain.entities.User
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.presentation.screens.composable.DropDownState

data class NewInvoiceUiState(
    val errorMessage: String = "",
    val errorState: ErrorState? = null,
    val isLoading: Boolean = false,
    val showDiscountDialog: Boolean = false,
    val showErrorScreen: Boolean = false,
    val isAddItem: Boolean = false,
    val showBarcodeReaderScreen: Boolean = false,
    val errorDialogueIsVisible: Boolean = false,
    val selectedItemsIndexFromAllItems: List<Int> = emptyList(),
    val selectedItemIndexFromInvoice: Int = -1,
    val invoiceItemList: List<NewInvoiceItemUiState> = emptyList(),
    val allItemsList: Flow<PagingData<ItemUiState>> = emptyFlow(),
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
    val discounts: List<DiscountDataState> = listOf(
        DiscountDataState(0, "", "", value = 0f),
    ),
    val selectedInvoiceDiscount: DiscountDataState = discounts.first(),
    val selectedItemDiscount: DiscountDataState = discounts.first(),
    val selectedInvoiceType: InvoiceDataState = invoiceTypes.first(),
    val invoiceNumber: Int = 0,
    val cashierName: String = RetailSetup.CASHIER_NAME,
    val comment: String = "",
    val calculations: Calculations = Calculations(),
    val calculationItem: Calculations = calculations.copy(discountAmount = 0f),
    val itemId: Long = 0L,
)

data class Calculations(
    val subTotal: Float = 0f,
    val totalTax: Float = 0f,
    val netTotal: Float = 0f,
    val fee: Float = 0f,
    val amount: Float = 0f,
    val totalPaid: Float = 0f,
    val remaining: Float = 0f,
    val taken: Float = 0f,
    val given: Float = 0f,
    val discountAmount: Float = 0f,
)

@Immutable
data class InvoiceDataState(
    val id: Long = 0L,
    val name: String = ""
)

data class DiscountDataState(
    val id: Long = 0L,
    val name: String = "",
    val type: String = "",
    val value: Float = 0f,
)

fun InvoiceDataState.toDropDownState(): DropDownState = DropDownState(
    id, name
)

fun DiscountDataState.toDropDownState(): DropDownState = DropDownState(
    id, name
)

fun Store.toInvoiceDataState(): InvoiceDataState = InvoiceDataState(
    storeId.toLong(), name
)

fun Customer.toInvoiceDataState(): InvoiceDataState = InvoiceDataState(
    id, "$firstName $lastName"
)

fun User.toInvoiceDataState(): InvoiceDataState = InvoiceDataState(
    id.toLong(), name
)

enum class ExpandedCardStatus {
    Items, Brandon
}

data class NewInvoiceItemUiState(
    val itemID: Long = 10000003869,
    val itemCode: Int = 132,
    val alu: String = "654",
    val name: String = "marnasdasdasd",
    var qty: String = "1",
    val orgPrice: Float = 20f,
    val itemDisc: Float = 20f,
    val price: Float = 20f,
    val extPrice: Float = 20f,
    val priceWOT: Float = 20f,
    val taxPerc: Float = 20f,
    val taxAmount: Float = 20f,
    val itemSerial: Int = 20,
)

data class ItemUiState(
    val itemID: Long = 10000003869,
    val itemCode: Int = 3465,
    val upc: Int = 654,
    val alu: String = "654",
    val cost: Float = 1f,
    val name: String = "طقم ملايه بولي قطن",
    val name2: String = "uhukhkjhk",
    val description: String = " خسعيابستي بمتس بهتسي بمتسيمنب تسيمن بتمينتب منسيت بمنيست بمنتسيم بنتيسمن تبمن",
    val styleId: Int = 0,
    val styleName: String = "احمر",
    val onHand: Float = 51f,
    val freeCard: Boolean = true,
    val freeCardPrice: Int = 10,
    val price: Float = 1000f,
    val taxPerc: Float = 20f,
    val qty: Float = 1f,
    val itemDiscount: Float = 1f,
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
        cost = cost,
        upc = this.upc,
        alu = this.alu,
        name = this.name,
        name2 = this.name2,
        description = this.description,
        styleId = this.styleId,
        styleName = this.styleName,
        onHand = this.onHand,
        freeCard = this.freeCard,
        freeCardPrice = this.freeCardPrice.toInt(),
        price = this.price,
        taxPerc = this.taxPerc,
        qty = this.qty,
        itemDiscount = this.itemDiscount,
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
    itemID = itemID,
    itemCode = itemCode,
    alu = alu,
    name = name,
    qty = qty.toString(),
    orgPrice = price,
    itemDisc = itemDiscount,
    price = price,
    extPrice = price,
    priceWOT = price,
    taxPerc = taxPerc,
    taxAmount = price,
    itemSerial = 0,
)

val list = mutableListOf<ItemUiState>()

fun Flow<PagingData<Item>>.toUIState(): Flow<PagingData<ItemUiState>> {
    return this.map { pagingData ->
        pagingData.map {
            list.add(it.toUiState())
            it.toUiState()
        }
    }
}