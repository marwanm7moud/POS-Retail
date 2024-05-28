package org.abapps.app.data.gateway.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.abapps.app.data.gateway.BaseGateway
import org.abapps.app.data.remote.mapper.toEntity
import org.abapps.app.data.remote.model.CustomerDto
import org.abapps.app.data.remote.model.DiscountDto
import org.abapps.app.data.remote.model.ItemDto
import org.abapps.app.data.remote.model.ServerResponse
import org.abapps.app.data.remote.model.StoreDto
import org.abapps.app.data.remote.model.UserDto
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
        return tryToExecute<ServerResponse<List<CustomerDto>>> {
            get("customers") {
                parameter("storeId", storeId)
                parameter("subCompanyId", subCompanyId)
            }
        }.data?.map { it.toEntity() } ?: throw NotFoundException("Customers not found")
    }

    override suspend fun getStoresBySubCompanyId(subCompanyId: Int): List<Store> {
        return tryToExecute<ServerResponse<List<StoreDto>>> {
            get("stores/$subCompanyId")
        }.data?.map { it.toEntity() } ?: throw NotFoundException("Stores not found")
    }

    override suspend fun getAllDiscounts(subCompanyId: Int): List<Discount> {
        return tryToExecute<ServerResponse<List<DiscountDto>>> {
            get("discounts/$subCompanyId")
        }.data?.map { it.toEntity() } ?: throw NotFoundException("Discounts not found")
    }

    override suspend fun getAllSalePersons(storeId: Int, sComId: Int): List<User> {
        return tryToExecute<ServerResponse<List<UserDto>>> {
            get("sales/person") {
                parameter("storeId", storeId)
                parameter("subCompanyId", sComId)
            }
        }.data?.map { it.toEntity() } ?: throw NotFoundException("Users not found")
    }

}