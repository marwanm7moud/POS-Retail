package org.abapps.app.data.remote.mapper

import org.abapps.app.data.remote.model.InvoiceSetupDto
import org.abapps.app.data.remote.model.StoreSetupDto
import org.abapps.app.data.remote.model.SubCompanySetupDto
import org.abapps.app.domain.entities.InvoiceSetup
import org.abapps.app.domain.entities.StoreSetup
import org.abapps.app.domain.entities.SubCompanySetup

fun SubCompanySetupDto.toEntity(): SubCompanySetup {
    return SubCompanySetup(
        sComId = this.sComId ?: 0,
        vat = this.vat ?: false,
        lenDecimal = this.lenDecimal ?: 0,
        createDate = this.createDate ?: "",
        modifiedDate = this.modifiedDate ?: "",
        userId = this.userId ?: 0,
        calcDiscItemOnPriceWot = this.calcDiscItemOnPriceWot ?: false,
        calcCostFifo = this.calcCostFifo ?: false,
        calcCostAverage = this.calcCostAverage ?: false,
        calcCostLastCost = this.calcCostLastCost ?: false,
        calcCostLifo = this.calcCostLifo ?: false,
        roundPrice = this.roundPrice ?: 0f,
        calcDiscountBeforeTax = this.calcDiscountBeforeTax ?: false
    )
}


fun StoreSetupDto.toEntity(): StoreSetup {
    return StoreSetup(
        storeId = this.storeId ?: 0,
        defaultLang = this.defaultLang ?: "",
        defaultName = this.defaultName ?: "",
        priceLvlId = this.priceLvlId ?: 0,
        header1 = this.header1 ?: "",
        header2 = this.header2 ?: "",
        header3 = this.header3 ?: "",
        header4 = this.header4 ?: "",
        header5 = this.header5 ?: "",
        footer1 = this.footer1 ?: "",
        footer2 = this.footer2 ?: "",
        footer3 = this.footer3 ?: "",
        footer4 = this.footer4 ?: "",
        footer5 = this.footer5 ?: "",
        createDate = this.createDate ?: "",
        modifiedDate = this.modifiedDate ?: "",
        userId = this.userId ?: 0,
        usedInvoiceReceipt = this.usedInvoiceReceipt ?: false,
        useInfoItemCode = this.useInfoItemCode ?: false,
        useInfoupc = this.useInfoupc ?: false,
        useInfoAlu = this.useInfoAlu ?: false,
        dailyClosingTime = this.dailyClosingTime ?: "",
        requiredComment = this.requiredComment ?: false,
        useCashDrawer = this.useCashDrawer ?: false,
        autoCloseDrawer = this.autoCloseDrawer ?: false,
        openDrawerWithClosedAmt = this.openDrawerWithClosedAmt ?: false,
        useInfoName = this.useInfoName ?: false,
        useInfoName2 = this.useInfoName2 ?: false,
        useInfoDesc = this.useInfoDesc ?: false,
        useInfoStyleName = this.useInfoStyleName ?: false,
        useInfoGrid1 = this.useInfoGrid1 ?: false,
        useInfoGrid2 = this.useInfoGrid2 ?: false,
        useInfoGrid3 = this.useInfoGrid3 ?: false,
        useInfoUdf1 = this.useInfoUdf1 ?: false,
        useInfoUdf2 = this.useInfoUdf2 ?: false,
        useInfoUdf3 = this.useInfoUdf3 ?: false,
        useInfoUDf4 = this.useInfoUDf4 ?: false,
        useInfoUDf5 = this.useInfoUDf5 ?: false,
        useInfoUDf6 = this.useInfoUDf6 ?: false,
        useInfoUDf7 = this.useInfoUDf7 ?: false,
        useInfoUDf8 = this.useInfoUDf8 ?: false,
        invcPriceWot = this.invcPriceWot ?: false,
        invcTaxPerc = this.invcTaxPerc ?: false,
        invcTaxAmt = this.invcTaxAmt ?: false,
        invcItemSerial = this.invcItemSerial ?: false
    )
}


fun InvoiceSetupDto.toEntity(): InvoiceSetup {
    return InvoiceSetup(
        storeId = this.storeId ?: 0,
        allawPartialPaymentOfCheck = this.allawPartialPaymentOfCheck ?: false,
        checkPrinter = this.checkPrinter ?: 0,
        defaultCust = this.defaultCust ?: 0,
        defaultSeller = this.defaultSeller ?: 0,
        closeInvcWhenBalanceZero = this.closeInvcWhenBalanceZero ?: false,
        multiSeller = this.multiSeller ?: false,
        allowInsertItemInTwoLine = this.allowInsertItemInTwoLine ?: false,
        countCopyReceipt = this.countCopyReceipt ?: 0,
        createDate = this.createDate ?: "",
        modifiedDate = this.modifiedDate ?: "",
        userId = this.userId ?: 0,
        allawSellWithNegativeOnHand = this.allawSellWithNegativeOnHand ?: false,
        quickPayCash = this.quickPayCash ?: false,
        requiredReturnReason = this.requiredReturnReason ?: false,
        setCancelInvcNo0 = this.setCancelInvcNo0 ?: false,
        validateReturn = this.validateReturn ?: false,
        noOfDayReturn = this.noOfDayReturn ?: 0,
        returnOnSale = this.returnOnSale ?: false,
        returnSamePaymentType = this.returnSamePaymentType ?: false,
        returnPaymentType = this.returnPaymentType ?: 0,
        autoPrintGiftRec = this.autoPrintGiftRec ?: false,
        firstName = this.firstName ?: "",
        name = this.name ?: ""
    )
}