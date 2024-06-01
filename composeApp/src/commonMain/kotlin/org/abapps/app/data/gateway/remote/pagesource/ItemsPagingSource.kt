package org.abapps.app.data.gateway.remote.pagesource

import org.abapps.app.data.util.RetailSetup
import org.abapps.app.domain.entities.Item
import org.abapps.app.domain.entities.PaginationItems
import org.abapps.app.domain.gateway.IInvoiceGateway
import kotlin.properties.Delegates

class ItemsPagingSource(
    private val remoteGateway: IInvoiceGateway,
) : BasePagingSource<Item>() {

    private var customerId by Delegates.notNull<Long>()

    fun initCustomer(id: Long) {
        customerId = id
    }

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Item> {
        return remoteGateway.getAllItems(
            RetailSetup.STORE_ID,
            RetailSetup.SUB_COMPANY_ID,
            customerId,
            RetailSetup.FIFO || RetailSetup.AVERAGE,
            RetailSetup.PRICE_LVL_ID,
            page,
            limit
        )
    }

}