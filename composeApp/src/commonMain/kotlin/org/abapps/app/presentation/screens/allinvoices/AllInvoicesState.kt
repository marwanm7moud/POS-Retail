package org.abapps.app.presentation.screens.allinvoices

import org.abapps.app.presentation.base.ErrorState

data class AllInvoicesState (
    val errorMessage: String = "",
    val errorState: ErrorState? = null,
    val isLoading: Boolean = false,
)