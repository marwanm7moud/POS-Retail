package org.abapps.app.domain.usecase

import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.abapps.app.data.gateway.remote.pagesource.ItemsPagingSource
import org.abapps.app.domain.entities.Customer
import org.abapps.app.domain.entities.Discount
import org.abapps.app.domain.entities.Item
import org.abapps.app.domain.entities.Store
import org.abapps.app.domain.entities.User
import org.abapps.app.domain.gateway.IInvoiceGateway

class ManageInvoiceUseCase(
    private val invoiceGateway: IInvoiceGateway,
    private val items: ItemsPagingSource,
) {
    suspend fun getAllItems(customerId: Long): Flow<PagingData<Item>> {
        items.initCustomer(customerId)
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { items },
        ).flow
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