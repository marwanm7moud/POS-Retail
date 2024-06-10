package org.abapps.app.printer

import android.content.Context
import android.widget.Toast
import net.posprinter.IConnectListener
import net.posprinter.IDeviceConnection
import net.posprinter.POSConnect
import net.posprinter.TSPLConst
import net.posprinter.TSPLPrinter
import org.abapps.app.util.Printer

class AndroidPrinter(val context: Context) : Printer {

    var curConnect: IDeviceConnection? = null
    var printer: TSPLPrinter? = null

    init {
        POSConnect.init(this.context)
    }

    override fun connectBtandPrintReceipt(macAddress: String) {
        val connectListener = IConnectListener { code, connInfo, msg ->
            when (code) {
                POSConnect.CONNECT_SUCCESS -> {
                    Toast.makeText(context, "Connect success", Toast.LENGTH_SHORT).show()
                    printer= TSPLPrinter(curConnect)
                    printReceipt()
                }

                POSConnect.CONNECT_FAIL -> {
                    Toast.makeText(context, "Connect failed : ${connInfo} - - $msg", Toast.LENGTH_SHORT).show()
                }

                POSConnect.CONNECT_INTERRUPT -> {
                    Toast.makeText(context, "connection has disconnected", Toast.LENGTH_SHORT).show()
                }

                POSConnect.SEND_FAIL -> {
                    Toast.makeText(context, "Send Faileds", Toast.LENGTH_SHORT).show()
                }
            }
        }
        curConnect?.close()
        curConnect = POSConnect.createDevice(POSConnect.DEVICE_TYPE_BLUETOOTH)
        curConnect!!.connect(macAddress, connectListener)
    }

    private fun printReceipt() {
        printer!!.sizeMm(60.0, 15.0)
            .density(10)
            .reference(0, 0)
            .direction(TSPLConst.DIRECTION_FORWARD)
            .cls()
            .text(10, 10, TSPLConst.FNT_8_12, 2, 2, "ksm el CC")
            .print(2)
    }

}