package org.abapps.app.domain.gateway

import org.abapps.app.domain.entities.Customer
import org.abapps.app.domain.entities.Discount
import org.abapps.app.domain.entities.Invoice
import org.abapps.app.domain.entities.Item
import org.abapps.app.domain.entities.PaginationItems
import org.abapps.app.domain.entities.Store
import org.abapps.app.domain.entities.User

interface IInvoiceGateway {
    suspend fun getAllItems(
        storeId: Int,
        sComId: Int,
        customerId: Long,
        isAverageOrFifo: Boolean,
        priceLvlId: Int,
        page: Int,
        pageSize: Int,
    ): PaginationItems<Item>

    suspend fun getCustomers(storeId: Int, subCompanyId: Int): List<Customer>
    suspend fun getStoresBySubCompanyId(subCompanyId: Int): List<Store>
    suspend fun getAllDiscounts(subCompanyId: Int): List<Discount>
    suspend fun getAllSalePersons(storeId: Int, sComId: Int): List<User>

    suspend fun getAllInvoices(
        storeId: Int,
        sComId: Int,
        page: Int,
        pageSize: Int,
    ) : PaginationItems<Invoice>
}