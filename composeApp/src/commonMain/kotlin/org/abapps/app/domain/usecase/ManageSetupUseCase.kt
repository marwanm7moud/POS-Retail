package org.abapps.app.domain.usecase

import org.abapps.app.domain.entities.InvoiceSetup
import org.abapps.app.domain.entities.StoreSetup
import org.abapps.app.domain.entities.SubCompanySetup
import org.abapps.app.domain.gateway.ISetupGateway

class ManageSetupUseCase(
    private val setupGateway: ISetupGateway
) {
    suspend fun getSubCompanySetup(sComId: String): SubCompanySetup {
        return setupGateway.getSubCompanySetup(sComId)
    }

    suspend fun getSetupStore(storeId: String): StoreSetup {
        return setupGateway.getSetupStore(storeId)
    }

    suspend fun getInvoiceSetup(storeId: String): InvoiceSetup {
        return setupGateway.getInvoiceSetup(storeId)
    }

    suspend fun getMainStoreId(sComId: String): Int {
        return setupGateway.getMainStoreId(sComId)
    }
}