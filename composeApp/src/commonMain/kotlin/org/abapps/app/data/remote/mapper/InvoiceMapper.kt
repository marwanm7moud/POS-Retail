package org.abapps.app.data.remote.mapper

import org.abapps.app.data.remote.model.CustomerDto
import org.abapps.app.data.remote.model.DiscountDto
import org.abapps.app.data.remote.model.StoreDto
import org.abapps.app.data.remote.model.UserDto
import org.abapps.app.domain.entities.Customer
import org.abapps.app.domain.entities.Discount
import org.abapps.app.domain.entities.Store
import org.abapps.app.domain.entities.User

fun CustomerDto.toEntity(): Customer {
    return Customer(
        id = this.id ?: 0,
        code = this.code ?: "",
        firstName = this.firstName ?: "",
        lastName = this.lastName ?: "",
        fullName = this.fullName ?: "",
        scomId = this.scomId ?: 0,
        storeId = this.storeId ?: 0,
        className = this.className ?: "",
        storeName = this.storeName ?: "",
        priceLevel = this.priceLevel ?: 0,
        discId = this.discId ?: 0,
        customerClassId = this.customerClassId ?: 0,
        active = this.active ?: false,
        reasonDeActive = this.reasonDeActive ?: "",
        phone = this.phone ?: "",
        phone2 = this.phone2 ?: "",
        phone3 = this.phone3 ?: "",
        address = this.address ?: "",
        address2 = this.address2 ?: "",
        email = this.email ?: "",
        socialId = this.socialId ?: "",
        ar = this.ar ?: false,
        creditLimit = this.creditLimit ?: 0.0f,
        createDate = this.createDate ?: "",
        modifiedDate = this.modifiedDate ?: "",
        userID = this.userID ?: 0,
        eType = this.eType ?: "",
        eIdNumber = this.eIdNumber ?: "",
        totalInvoices = this.totalInvoices ?: 0.0f,
        returnedInvoices = this.returnedInvoices ?: 0,
        invoicesCount = this.invoicesCount ?: 0,
        countReturned = this.countReturned ?: 0
    )
}


fun StoreDto.toEntity(): Store {
    return Store(
        storeId = this.storeId ?: 0,
        code = this.code ?: "",
        name = this.name ?: "",
        name2 = this.name2 ?: "",
        address = this.address ?: "",
        phone = this.phone ?: "",
        mobile = this.mobile ?: "",
        fax = this.fax ?: "",
        email = this.email ?: "",
        logo = this.logo ?: "",
        active = this.active ?: false,
        scomId = this.scomId ?: 0,
        createDate = this.createDate ?: "",
        modifiedDate = this.modifiedDate ?: "",
        userId = this.userId ?: 0,
        franchise = this.franchise ?: false,
        priceLvlId = this.priceLvlId ?: 0,
        rin = this.rin ?: "",
        companyTradeName = this.companyTradeName ?: "",
        country = this.country ?: "",
        branchCode = this.branchCode ?: "",
        governate = this.governate ?: "",
        regionCity = this.regionCity ?: "",
        street = this.street ?: "",
        activityCode = this.activityCode ?: "",
        deviceSerial = this.deviceSerial ?: "",
        buildingNumber = this.buildingNumber ?: "",
        postalCode = this.postalCode ?: "",
        floor = this.floor ?: "",
        room = this.room ?: "",
        clientId = this.clientId ?: "",
        secret1 = this.secret1 ?: "",
        secret2 = this.secret2 ?: "",
        landmark = this.landmark ?: "",
        taxpayerActivityCode = this.taxpayerActivityCode ?: "",
        taxId = this.taxId ?: "",
        posSerial = this.posSerial ?: "",
        posClientId = this.posClientId ?: "",
        posClientSecret = this.posClientSecret ?: "",
        posName = this.posName ?: "",
        os = this.os ?: "",
        lastUuid = this.lastUuid ?: ""
    )
}


fun DiscountDto.toEntity(): Discount {
    return Discount(
        discId = this.discId ?: 0,
        code = this.code ?: "",
        name = this.name ?: "",
        discType = this.discType ?: "",
        value = this.value ?: 0.0f,
        manager = this.manager ?: false,
        active = this.active ?: false,
        createDate = this.createDate ?: "",
        modifiedDate = this.modifiedDate ?: "",
        userId = this.userId ?: 0,
        scomId = this.scomId ?: 0,
        excludeItemsDiscount = this.excludeItemsDiscount ?: false,
        promoCode = this.promoCode ?: false,
        promoCodeId = this.promoCodeId ?: 0
    )
}


fun UserDto.toEntity(): User {
    return User(
        id = this.id ?: 0,
        name = this.name ?: "",
        password = this.password ?: "",
        userName = this.userName ?: "",
        userClassId = this.userClassId ?: 0,
        myLanguage = this.myLanguage ?: "",
        title = this.title ?: "",
        fullName = this.fullName ?: "",
        address = this.address ?: "",
        job = this.job ?: "",
        phone = this.phone ?: "",
        mobile = this.mobile ?: "",
        email = this.email ?: "",
        passCode = this.passCode ?: "",
        active = this.active ?: false,
        createDate = this.createDate ?: "",
        modifiedDate = this.modifiedDate ?: "",
        userId = this.userId ?: 0,
        scomId = this.scomId ?: 0,
        storeId = this.storeId ?: 0,
        sales = this.sales ?: false
    )
}