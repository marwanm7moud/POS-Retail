package org.abapps.app.data.gateway.remote

import io.ktor.client.HttpClient
import org.abapps.app.data.gateway.BaseGateway
import org.abapps.app.domain.entities.InvoiceSetup
import org.abapps.app.domain.entities.StoreSetup
import org.abapps.app.domain.entities.SubCompanySetup
import org.abapps.app.domain.gateway.ISetupGateway

class SetupGateway(client: HttpClient):BaseGateway(client),ISetupGateway {
    override suspend fun getSubCompanySetup(sComId: String): SubCompanySetup {
        TODO("Not yet implemented")
    }

    override suspend fun getSetupStore(storeId: String): StoreSetup {
        TODO("Not yet implemented")
    }

    override suspend fun getInvoiceSetup(storeId: String): InvoiceSetup {
        TODO("Not yet implemented")
    }
}