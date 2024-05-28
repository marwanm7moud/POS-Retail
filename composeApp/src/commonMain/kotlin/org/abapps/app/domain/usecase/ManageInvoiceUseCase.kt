package org.abapps.app.domain.usecase

import org.abapps.app.domain.entities.Item
import org.abapps.app.domain.gateway.IInvoiceGateway

class ManageInvoiceUseCase(
    private val invoiceGateway: IInvoiceGateway,
) {
    suspend fun getAllItems(
        storeId: Int,
        subCompanyId: Int,
        customerId: Long,
        isAverageOrFifo: Boolean
    ): List<Item> {
        return invoiceGateway.getAllItems(storeId, subCompanyId, customerId, isAverageOrFifo)
    }
}