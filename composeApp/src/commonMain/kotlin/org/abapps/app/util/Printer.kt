package org.abapps.app.util

import org.abapps.app.PlatformContext

interface Printer {
    fun connectBtandPrintReceipt(macAddress: String)
}

expect fun createPrinter(context: PlatformContext): Printer

expect fun browseBluetoothDevices(context: PlatformContext, onSelectDevice: (String?) -> Unit)
