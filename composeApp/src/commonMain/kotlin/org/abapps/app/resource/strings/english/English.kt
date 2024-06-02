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
    override val invoiceNumber: String = "Invc No",
    override val invoiceType: String = "Invc Type",
    override val status: String = "Status",
    override val regular: String = "Regular",
    override val returnInvoice: String = "Return",
    override val customer: String = "Customer",
    override val sourceType: String = "Source Type",
    override val store: String = "Store",
    override val cashier: String = "Cashier",
    override val salesPerson: String = "Sales Person",
    override val comment: String = "Comment",
    override val brandon: String = "Brandon",
    override val items: String = "Items",
    override val allItems: String = "All Items",
    override val addItem: String = "Add Item",
    override val subTotal: String = "Sub Total",
    override val totalTax: String = "Total Tax",
    override val netTotal: String = "Net Total",
    override val fee: String = "Fee",
    override val amount: String = "Amount",
    override val totalPaid: String = "Total Paid",
    override val remaining: String = "Remaining",
    override val taken: String = "Taken",
    override val given: String = "Given", override val discount: String = "Discount",
    override val discountAmount: String = "Discount Amount",
    override val pay: String = "Pay",
    override val percentage: String = "Percentage", override val scanQr: String = "Scan QR",
) : IStringResources