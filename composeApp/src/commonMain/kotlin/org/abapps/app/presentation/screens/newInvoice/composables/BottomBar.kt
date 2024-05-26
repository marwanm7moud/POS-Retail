package org.abapps.app.presentation.screens.newInvoice.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.StOutlinedButton

@Composable
fun CalculationsBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            .background(Color(0xFF0202020))
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                CalculationItem("Sub Total", "1000")
                CalculationItem("Total Tax", "140")
                Spacer(modifier = Modifier.height(16.dp))
                CalculationItem("Net Total", "1000")
                CalculationItem("Fee", "0")
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                CalculationItem("Amount", "1140")
                CalculationItem("Total Paid", "0")
                CalculationItem("Remaining", "1140")
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.border(
                    BorderStroke(0.5.dp, Color.LightGray),
                    RoundedCornerShape(12.dp)
                ).padding(16.dp)
            ) {
                CalculationItem("Taken", "1140")
                CalculationItem("Given", "0")
            }
            StOutlinedButton(
                title = "Update",
                onClick = {},
                modifier = Modifier
            )
        }
    }
}

@Composable
fun CalculationItem(title: String, calculatedNumber: String) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, color = Color.White, modifier = Modifier.width(80.dp))
        Text(
            text = calculatedNumber,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(120.dp)
                .border(BorderStroke(0.5.dp, Color.LightGray), RoundedCornerShape(12.dp))
                .padding(vertical = 8.dp)
        )
    }
}