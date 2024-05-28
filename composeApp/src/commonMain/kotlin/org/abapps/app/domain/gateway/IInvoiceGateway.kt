package org.abapps.app.domain.gateway

import org.abapps.app.domain.entities.Item

interface IInvoiceGateway {
    suspend fun getAllItems(
        storeId: Int,
        sComId: Int,
        customerId: Long,
        isAverageOrFifo: Boolean
    ): List<Item>
}