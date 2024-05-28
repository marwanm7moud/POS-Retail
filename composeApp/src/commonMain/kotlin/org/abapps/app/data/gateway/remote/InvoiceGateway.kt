package org.abapps.app.data.gateway.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.abapps.app.data.gateway.BaseGateway
import org.abapps.app.data.remote.mapper.toEntity
import org.abapps.app.data.remote.model.ItemDto
import org.abapps.app.data.remote.model.ServerResponse
import org.abapps.app.domain.entities.Customer
import org.abapps.app.domain.entities.Discount
import org.abapps.app.domain.entities.Item
import org.abapps.app.domain.entities.Store
import org.abapps.app.domain.entities.User
import org.abapps.app.domain.gateway.IInvoiceGateway
import org.abapps.app.domain.util.NotFoundException

class InvoiceGateway(client: HttpClient) : BaseGateway(client), IInvoiceGateway {
    override suspend fun getAllItems(
        storeId: Int,
        sComId: Int,
        customerId: Long,
        isAverageOrFifo: Boolean
    ): List<Item> {
        return tryToExecute<ServerResponse<List<ItemDto>>> {
            get("items") {
                parameter("storeId", storeId)
                parameter("subCompanyId", sComId)
                parameter("customerId", customerId)
                parameter("isAverageOrFifo", isAverageOrFifo)
            }
        }.data?.take(20)?.map { it.toEntity() } ?: throw NotFoundException("Items not found")
    }

    override suspend fun getCustomers(storeId: Int, subCompanyId: Int): List<Customer> {
        TODO("Not yet implemented")
    }

    override suspend fun getStoresBySubCompanyId(subCompanyId: Int): List<Store> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDiscounts(subCompanyId: Int): List<Discount> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSalePersons(storeId: Int, sComId: Int): List<User> {
        TODO("Not yet implemented")
    }

}