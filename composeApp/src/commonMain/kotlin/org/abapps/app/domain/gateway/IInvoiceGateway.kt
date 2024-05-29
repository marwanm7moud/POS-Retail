package org.abapps.app.domain.gateway

import org.abapps.app.domain.entities.Customer
import org.abapps.app.domain.entities.Discount
import org.abapps.app.domain.entities.Item
import org.abapps.app.domain.entities.Store
import org.abapps.app.domain.entities.User

interface IInvoiceGateway {
    suspend fun getAllItems(
        storeId: Int,
        sComId: Int,
        customerId: Long,
        isAverageOrFifo: Boolean,
        priceLvlId: Int,
    ): List<Item>

    suspend fun getCustomers(storeId: Int, subCompanyId: Int): List<Customer>
    suspend fun getStoresBySubCompanyId(subCompanyId: Int): List<Store>
    suspend fun getAllDiscounts(subCompanyId: Int): List<Discount>
    suspend fun getAllSalePersons(storeId: Int, sComId: Int): List<User>
}