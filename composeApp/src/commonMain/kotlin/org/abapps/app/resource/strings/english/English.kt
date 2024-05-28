package org.abapps.app.resource.strings.english

import org.abapps.app.resource.strings.IStringResources

data class English(
    override val invoice: String = "Invoice",
    override val transfer: String = "Transfer",
    override val settings: String = "Settings",
    override val exit: String = "Exit",
    override val userName: String = "Username",
    override val password: String = "Password",
    override val ok: String = "Ok",
    override val cancel: String = "Cancel",
    override val tryAgain: String = "Try Again",
    override val somethingWrongHappened: String = "Something wrong happened please try again later!",
    override val noInternetMessage: String = "Internet is not available",
    override val noInternetDescription: String = "Please make sure you are connected to the internet and try again",
    override val nullDataMessage: String = "There are no data",
    override val nullDataDescription: String = "Enjoy adding items to your app and get ready to enjoy",
    override val enterYourInfo: String = "Your information",
    override val warning: String = "Warning",
    override val error: String = "Error",
    override val userNotFound: String = "User not found",
    override val enterYourPasscode: String = "Enter Passcode",
    override val retry: String = "Retry",
    override val doYouWantToCloseApp: String = "Do you want to close app",
    override val no: String = "No",
    override val yes: String = "Yes",
    override val logo: String = "Logo",
    override val logonError: String = "Logon Error",
) : IStringResources