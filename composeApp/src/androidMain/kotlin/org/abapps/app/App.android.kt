package org.abapps.app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import org.abapps.app.printer.Printer


class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

class AppActivity : ComponentActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val printer = Printer(context)
            //App()
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Row(modifier = Modifier) {
//                    Button(onClick = {
//                        browseBluetoothDevices(context) {
//                            printer.connectBt(it?.device?.address!!)
//                        }
//                    }) {
//                        Text(text = printer.curConnect?.connectInfo ?: "Default")
//                    }
//                    Button(onClick = {
//                        printer.printer!!.sizeMm(60.0, 15.0)
//                            .density(10)
//                            .reference(0, 0)
//                            .direction(TSPLConst.DIRECTION_FORWARD)
//                            .cls()
//                            .text(10, 10, TSPLConst.FNT_8_12, 2, 2, "ksm el CC")
//                            .print(2)
//
//
//                    }) {
//                        Text(text = "print")
//                    }
//                }
//            }
        }
    }
}

@SuppressLint("MissingPermission")
fun browseBluetoothDevices(context: Context, onSelectDevice: (BluetoothConnection?) -> Unit) {
    val bluetoothDevicesList = BluetoothPrintersConnections().list
    if (bluetoothDevicesList != null) {
        val items = arrayOfNulls<String>(bluetoothDevicesList.size + 1)
        items[0] = "Default printer"
        var i = 0
        for (device in bluetoothDevicesList) {
            items[++i] = device.device.name
        }

        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Bluetooth printer selection")
        alertDialog.setItems(
            items
        ) { dialogInterface: DialogInterface?, i1: Int ->
            val index = i1 - 1
            if (index == -1) {
                onSelectDevice(null)
            } else {
                onSelectDevice(bluetoothDevicesList[index])
            }
        }

        val alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }
}