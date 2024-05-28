package org.abapps.app.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.beepbeep.designSystem.ui.theme.Theme
import org.abapps.app.presentation.screens.allinvoices.AllInvoicesScreen
import org.abapps.app.presentation.screens.transferInvoices.TransferInvoicesScreen

class HomeScreen : Screen {
    @Composable
    override fun Content() {

        val nav = LocalNavigator.currentOrThrow


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Button(
                    modifier = Modifier.fillMaxWidth(0.5f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Theme.colors.primary),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { nav.push(AllInvoicesScreen()) }
                ) {
                    Text("POS")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(0.5f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Theme.colors.primary),
                    shape = RoundedCornerShape(12.dp),
                    onClick = { nav.push(TransferInvoicesScreen()) }
                ) {
                    Text("Transfer")
                }
            }
        }
    }
}