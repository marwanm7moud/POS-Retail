package org.abapps.app.presentation.screens.posInvoiceScreen.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.StOutlinedButton
import org.abapps.app.presentation.screens.posInvoiceScreen.NewInvoiceUiState
import org.abapps.app.resource.Resources

@Composable
fun CalculationsBar(state: NewInvoiceUiState) {
    var expandedState by remember { mutableStateOf(true) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                .background(Color(0xFF0202020)).clickable {
                    expandedState = !expandedState
                }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center

        ) {
            IconButton(
                modifier = Modifier
                    .alpha(0.5f)
                    .rotate(rotationState),
                onClick = { expandedState = !expandedState }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop-Down Arrow",
                    tint = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(topStart = 12.dp))
                .background(Color(0xFF0202020))
                .padding(16.dp)
        ) {
            androidx.compose.animation.AnimatedVisibility(expandedState) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        CalculationItem(Resources.strings.subTotal, state.invoiceItemList.sumOf { it.priceWOT.toDouble() }.toFloat()
                            .toString())
                        CalculationItem(Resources.strings.totalTax, state.invoiceItemList.sumOf { it.taxAmount.toDouble() }.toFloat()
                            .toString())
                        Spacer(modifier = Modifier.height(16.dp))
                        CalculationItem(Resources.strings.netTotal, "1000")
                        CalculationItem(Resources.strings.fee, "0")
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        CalculationItem(Resources.strings.amount, "1140")
                        CalculationItem(Resources.strings.totalPaid, "0")
                        CalculationItem(Resources.strings.remaining, "1140")
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.border(
                            BorderStroke(0.5.dp, Color.LightGray),
                            RoundedCornerShape(12.dp)
                        ).padding(16.dp)
                    ) {
                        CalculationItem(Resources.strings.taken, "1140")
                        CalculationItem(Resources.strings.given, "0")
                    }
                    StOutlinedButton(
                        title = "Update",
                        onClick = {},
                        modifier = Modifier
                    )
                }
            }
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