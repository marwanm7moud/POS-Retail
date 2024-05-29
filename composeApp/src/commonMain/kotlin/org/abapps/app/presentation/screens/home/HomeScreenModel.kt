package org.abapps.app.presentation.screens.home

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.abapps.app.data.util.AppLanguage
import org.abapps.app.data.util.RetailSetup
import org.abapps.app.domain.entities.InvoiceSetup
import org.abapps.app.domain.entities.StoreSetup
import org.abapps.app.domain.entities.SubCompanySetup
import org.abapps.app.domain.usecase.AdminSystemUseCase
import org.abapps.app.domain.usecase.ManageSetupUseCase
import org.abapps.app.domain.util.UnknownErrorException
import org.abapps.app.presentation.base.BaseScreenModel
import org.abapps.app.presentation.base.ErrorState
import org.abapps.app.resource.strings.IStringResources
import org.abapps.app.util.LanguageCode
import org.abapps.app.util.LocalizationManager

class HomeScreenModel(
    private val adminSystem: AdminSystemUseCase,
    private val manageSetup: ManageSetupUseCase,
) : BaseScreenModel<HomeState, HomeUiEffect>(HomeState()),
    HomeInteractionListener {
    override val viewModelScope: CoroutineScope get() = screenModelScope
    private var localMessage: IStringResources = LocalizationManager.getStringResources(
        LanguageCode.entries.find { code ->
            code.value == AppLanguage.code.value
        } ?: LanguageCode.EN
    )

    init {
        getSetup()
    }

    private fun getSetup() {
        updateState { it.copy(isLoading = true, errorState = null, errorMessage = "") }
        tryToExecute(
            function = { getCombinedSetup() },
            onSuccess = { combinedSetupResult ->
                updateState { it.copy(isLoading = false, errorState = null, errorMessage = "") }
                RetailSetup.apply {
                    VAT = combinedSetupResult.subCompanySetup.vat
                    LEN_DECIMAL = combinedSetupResult.subCompanySetup.lenDecimal
                    ROUND_PRICE = combinedSetupResult.subCompanySetup.roundPrice
                    TAX_EFFECT = combinedSetupResult.subCompanySetup.calcDiscountBeforeTax
                    TAX_EFFECT_WITH_ITEM =
                        combinedSetupResult.subCompanySetup.calcDiscItemOnPriceWot
                    FIFO = combinedSetupResult.subCompanySetup.calcCostFifo
                    LIFO = combinedSetupResult.subCompanySetup.calcCostLifo
                    AVERAGE = combinedSetupResult.subCompanySetup.calcCostAverage
                    LAST_COST = combinedSetupResult.subCompanySetup.calcCostLastCost
                    NUMBER_OF_DAY_RETURN = combinedSetupResult.invoiceSetup.noOfDayReturn
                    ALLOW_NEGATIVE_QTY =
                        combinedSetupResult.invoiceSetup.allawSellWithNegativeOnHand
                    DEFAULT_LANGUAGE = combinedSetupResult.storeSetup.defaultLang
                    DEFAULT_SALES_ID = combinedSetupResult.invoiceSetup.defaultSeller
                    DEFAULT_CUSTOMER_ID = combinedSetupResult.invoiceSetup.defaultCust
                    IS_MAIN_STORE = combinedSetupResult.mainStoreId == STORE_ID
                    CASHIER_NAME = combinedSetupResult.cashierName
                }
            },
            onError = ::onError
        )
    }

    private suspend fun getCombinedSetup(): CombinedSetupResult {
        return coroutineScope {
            val subCompanySetupDeferred = async {
                try {
                    manageSetup.getSubCompanySetup(RetailSetup.SUB_COMPANY_ID.toString())
                } catch (e: Exception) {
                    throw UnknownErrorException(e.message.toString())
                }
            }
            val storeSetupDeferred = async {
                try {
                    manageSetup.getSetupStore(RetailSetup.STORE_ID.toString())
                } catch (e: Exception) {
                    throw UnknownErrorException(e.message.toString())
                }
            }
            val invoiceSetupDeferred = async {
                try {
                    manageSetup.getInvoiceSetup(RetailSetup.STORE_ID.toString())
                } catch (e: Exception) {
                    throw UnknownErrorException(e.message.toString())
                }
            }
            val mainStoreIdDeferred = async {
                try {
                    manageSetup.getMainStoreId(RetailSetup.SUB_COMPANY_ID.toString())
                } catch (e: Exception) {
                    throw UnknownErrorException(e.message.toString())
                }
            }

            val cashierNameDeferred = async {
                try {
                    manageSetup.getCashierName(
                        RetailSetup.STORE_ID,
                        RetailSetup.SUB_COMPANY_ID,
                        RetailSetup.USER_ID
                    )
                } catch (e: Exception) {
                    throw UnknownErrorException(e.message.toString())
                }
            }

            val subCompanySetup = subCompanySetupDeferred.await()
            val storeSetup = storeSetupDeferred.await()
            val invoiceSetup = invoiceSetupDeferred.await()
            val mainStoreId = mainStoreIdDeferred.await()
            val cashierName = cashierNameDeferred.await()

            CombinedSetupResult(
                subCompanySetup = subCompanySetup,
                storeSetup = storeSetup,
                invoiceSetup = invoiceSetup,
                mainStoreId = mainStoreId,
                cashierName = cashierName,
            )
        }
    }


    override fun onClickInvoice() {
        sendNewEffect(HomeUiEffect.NavigateToInvoiceScreen)
    }

    override fun onClickTransfer() {
        sendNewEffect(HomeUiEffect.NavigateToTransferScreen)
    }

    fun retry() {
        getSetup()
    }

    override fun onUserNameChanged(username: String) {
        updateState {
            it.copy(
                settingsDialogueState = it.settingsDialogueState.copy(
                    username = username
                )
            )
        }
    }

    override fun onPasswordChanged(password: String) {
        updateState {
            it.copy(
                settingsDialogueState = it.settingsDialogueState.copy(
                    password = password
                )
            )
        }
    }

    override fun onClickExitApp() {
        showWarningDialogue()
    }

    fun exitApp() {
        updateState {
            it.copy(
                errorMessage = "",
                isLoading = false,
                errorState = null,
                exitApp = true,
                warningDialogueIsVisible = false
            )
        }
    }

    override fun onClickSettings() {
        updateState {
            it.copy(
                settingsDialogueState = SettingsDialogueState(
                    isVisible = true
                )
            )
        }
    }

    override fun onClickSettingsOk() {
        updateState {
            it.copy(
                errorState = null,
                errorMessage = "",
                settingsDialogueState = it.settingsDialogueState.copy(isLoading = true)
            )
        }
        if (adminSystem.checkPermissionWithPassword(state.value.settingsDialogueState.password)) {
            updateState {
                it.copy(
                    settingsDialogueState = it.settingsDialogueState.copy(
                        isLoading = false,
                        isVisible = false
                    )
                )
            }
            launchDelayed(1000) {
                updateState {
                    it.copy(
                        settingsDialogueState = SettingsDialogueState()
                    )
                }
            }
            sendNewEffect(HomeUiEffect.NavigateToSettingScreen)
        } else updateState {
            it.copy(
                errorMessage = localMessage.logonError,
                errorState = ErrorState.PermissionDenied(localMessage.logonError),
                settingsDialogueState = it.settingsDialogueState.copy(isLoading = false),
            )
        }
    }

    override fun showErrorDialogue() {
        updateState { it.copy(errorDialogueIsVisible = true, warningDialogueIsVisible = false) }
    }

    override fun showWarningDialogue() {
        updateState { it.copy(warningDialogueIsVisible = true) }
    }

    override fun onDismissErrorDialogue() {
        updateState {
            it.copy(
                errorDialogueIsVisible = false,
                warningDialogueIsVisible = false,
                errorState = null,
            )
        }
    }

    override fun onDismissWarningDialogue() {
        updateState {
            it.copy(
                warningDialogueIsVisible = false,
                errorState = null,
            )
        }
    }

    override fun onDismissSettingsDialogue() {
        updateState {
            it.copy(
                errorMessage = "",
                isLoading = false,
                errorState = null,
                settingsDialogueState = it.settingsDialogueState.copy(
                    isVisible = false,
                    isLoading = false
                )
            )
        }
    }

    private fun onFailed(errorState: ErrorState) {
        updateState {
            it.copy(
                errorState = errorState,
                isLoading = false,
                errorMessage = when (errorState) {
                    is ErrorState.UnknownError -> errorState.message.toString()
                    is ErrorState.ServerError -> errorState.message.toString()
                    is ErrorState.UnAuthorized -> errorState.message.toString()
                    is ErrorState.NotFound -> errorState.message.toString()
                    is ErrorState.ValidationNetworkError -> errorState.message.toString()
                    is ErrorState.NetworkError -> errorState.message.toString()
                    is ErrorState.ValidationError -> errorState.message.toString()
                    else -> "Something wrong happened please try again !"
                }
            )
        }
    }

    private fun onError(errorState: ErrorState) {
        updateState {
            it.copy(
                errorHomeDetailsState = errorState,
                isLoading = false,
                errorMessage = when (errorState) {
                    is ErrorState.UnknownError -> errorState.message.toString()
                    is ErrorState.ServerError -> errorState.message.toString()
                    is ErrorState.UnAuthorized -> errorState.message.toString()
                    is ErrorState.NotFound -> errorState.message.toString()
                    is ErrorState.ValidationNetworkError -> errorState.message.toString()
                    is ErrorState.NetworkError -> errorState.message.toString()
                    is ErrorState.ValidationError -> errorState.message.toString()
                    else -> "Something wrong happened please try again !"
                }
            )
        }
    }
}


data class CombinedSetupResult(
    val subCompanySetup: SubCompanySetup,
    val storeSetup: StoreSetup,
    val invoiceSetup: InvoiceSetup,
    val mainStoreId: Int,
    val cashierName: String,
)