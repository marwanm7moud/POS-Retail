package org.abapps.app.domain.usecase

import org.abapps.app.domain.entities.Customer
import org.abapps.app.domain.entities.Discount
import org.abapps.app.domain.entities.Item
import org.abapps.app.domain.entities.Store
import org.abapps.app.domain.entities.User
import org.abapps.app.domain.gateway.IInvoiceGateway

class ManageInvoiceUseCase(
    private val invoiceGateway: IInvoiceGateway,
) {
    suspend fun getAllItems(
        storeId: Int,
        subCompanyId: Int,
        customerId: Long,
        isAverageOrFifo: Boolean,
        priceLvlId: Int,
        page: Int,
        pageSize: Int,
    ): List<Item> {
        return invoiceGateway.getAllItems(
            storeId,
            subCompanyId,
            customerId,
            isAverageOrFifo,
            priceLvlId,
            page,
            pageSize
        ).items
    }

    suspend fun getCustomers(storeId: Int, subCompanyId: Int): List<Customer> {
        return invoiceGateway.getCustomers(storeId, subCompanyId)
    }

    suspend fun getStores(subCompanyId: Int): List<Store> {
        return invoiceGateway.getStoresBySubCompanyId(subCompanyId)
    }

    suspend fun getAllSales(storeId: Int, sComId: Int): List<User> {
        return invoiceGateway.getAllSalePersons(storeId, sComId)
    }

    suspend fun getAllDiscounts(subCompanyId: Int): List<Discount> {
        return invoiceGateway.getAllDiscounts(subCompanyId)
    }
}