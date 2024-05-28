package org.abapps.app.data.gateway.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.abapps.app.data.gateway.BaseGateway
import org.abapps.app.data.remote.mapper.toEntity
import org.abapps.app.data.remote.model.InvoiceSetupDto
import org.abapps.app.data.remote.model.ServerResponse
import org.abapps.app.data.remote.model.StoreSetupDto
import org.abapps.app.data.remote.model.SubCompanySetupDto
import org.abapps.app.domain.entities.InvoiceSetup
import org.abapps.app.domain.entities.StoreSetup
import org.abapps.app.domain.entities.SubCompanySetup
import org.abapps.app.domain.gateway.ISetupGateway
import org.abapps.app.domain.util.NotFoundException

class SetupGateway(client: HttpClient) : BaseGateway(client), ISetupGateway {
    override suspend fun getSubCompanySetup(sComId: String): SubCompanySetup {
        return tryToExecute<ServerResponse<SubCompanySetupDto>> {
            get("companySetup/$sComId")
        }.data?.toEntity() ?: throw NotFoundException("SubCompany setup not found")
    }

    override suspend fun getSetupStore(storeId: String): StoreSetup {
        return tryToExecute<ServerResponse<StoreSetupDto>> {
            get("storeSetup/$storeId")
        }.data?.toEntity() ?: throw NotFoundException("Store setup not found")
    }

    override suspend fun getInvoiceSetup(storeId: String): InvoiceSetup {
        return tryToExecute<ServerResponse<InvoiceSetupDto>> {
            get("invoiceSetup/$storeId")
        }.data?.toEntity() ?: throw NotFoundException("Invoice setup not found")
    }
}