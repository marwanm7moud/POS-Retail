package org.abapps.app.data.gateway.remote.pagesource

import org.abapps.app.data.util.RetailSetup
import org.abapps.app.domain.entities.Invoice
import org.abapps.app.domain.entities.PaginationItems
import org.abapps.app.domain.gateway.IInvoiceGateway

class InvoicesPagingSource(
    private val remoteGateway: IInvoiceGateway,
) : BasePagingSource<Invoice>() {
    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Invoice> {
        return remoteGateway.getAllInvoices(
            RetailSetup.STORE_ID,
            RetailSetup.SUB_COMPANY_ID,
            page,
            limit
        )
    }
}