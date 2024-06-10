package org.abapps.app

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import org.abapps.app.presentation.app.App
import org.abapps.app.printer.AndroidPrinter


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
            val printer = AndroidPrinter(context)
            App()
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