package org.abapps.app.domain.gateway

import org.abapps.app.domain.entities.InvoiceSetup
import org.abapps.app.domain.entities.StoreSetup
import org.abapps.app.domain.entities.SubCompanySetup

interface ISetupGateway {
    suspend fun getSubCompanySetup(sComId: String): SubCompanySetup
    suspend fun getSetupStore(storeId: String): StoreSetup
    suspend fun getInvoiceSetup(storeId: String): InvoiceSetup
    suspend fun getMainStoreId(sComId: String): Int
    suspend fun getCashierName(storeId: Int,sComId: Int,userId:Int): String
}