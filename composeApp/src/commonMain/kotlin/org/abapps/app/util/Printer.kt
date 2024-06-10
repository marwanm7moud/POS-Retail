package org.abapps.app.util

import org.abapps.app.PlatformContext

interface Printer {
    fun connectBt(macAddress: String)
    fun init()
}

expect fun createPrinter(context: PlatformContext): Printer

expect fun browseBluetoothDevices(context: PlatformContext, onSelectDevice: (String?) -> Unit)
