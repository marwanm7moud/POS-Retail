package org.abapps.app.util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import org.abapps.app.PlatformContext
import org.abapps.app.printer.AndroidPrinter

actual fun createPrinter(context: PlatformContext): Printer {
    return AndroidPrinter(context.androidContext)
}

@SuppressLint("MissingPermission")
actual fun browseBluetoothDevices(
    context: PlatformContext,
    onSelectDevice: (String?) -> Unit
) {
    val bluetoothDevicesList = BluetoothPrintersConnections().list
    if (bluetoothDevicesList != null) {
        val items = arrayOfNulls<String>(bluetoothDevicesList.size + 1)
        items[0] = "Default printer"
        var i = 0
        for (device in bluetoothDevicesList) {
            items[++i] = device.device.name
        }

        val alertDialog = AlertDialog.Builder(context.androidContext)
        alertDialog.setTitle("Bluetooth printer selection")
        alertDialog.setItems(
            items
        ) { dialogInterface: DialogInterface?, i1: Int ->
            val index = i1 - 1
            if (index == -1) {
                onSelectDevice(null)
            } else {
                onSelectDevice(bluetoothDevicesList[index].device.address)
            }
        }

        val alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

}