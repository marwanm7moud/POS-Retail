package org.abapps.app.printer

import android.content.Context
import android.widget.Toast
import net.posprinter.IConnectListener
import net.posprinter.IDeviceConnection
import net.posprinter.POSConnect
import net.posprinter.TSPLPrinter
import org.abapps.app.util.Printer

class AndroidPrinter(val context: Context) : Printer {

    init {
        POSConnect.init(context)
    }
    var curConnect: IDeviceConnection? = null
    var printer: TSPLPrinter? = null


    private val connectListener = IConnectListener { code, connInfo, msg ->
        when (code) {
            POSConnect.CONNECT_SUCCESS -> {
                Toast.makeText(context, "Connect success", Toast.LENGTH_SHORT).show()
                printer= TSPLPrinter(curConnect)
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


    override fun init(){
        POSConnect.init(this.context)
    }
    override fun connectBt(macAddress: String) {
        curConnect?.close()
        curConnect = POSConnect.createDevice(POSConnect.DEVICE_TYPE_BLUETOOTH)
        curConnect!!.connect(macAddress, connectListener)
    }

}