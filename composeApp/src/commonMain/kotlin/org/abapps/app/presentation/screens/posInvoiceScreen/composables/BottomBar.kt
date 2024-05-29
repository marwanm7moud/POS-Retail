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
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.StOutlinedButton
import org.abapps.app.presentation.screens.composable.DropDownTextField
import org.abapps.app.presentation.screens.posInvoiceScreen.InvoiceInteractions
import org.abapps.app.presentation.screens.posInvoiceScreen.NewInvoiceUiState
import org.abapps.app.presentation.screens.posInvoiceScreen.toDropDownState
import org.abapps.app.resource.Resources

@Composable
fun CalculationsBar(
    state: NewInvoiceUiState,
    listener: InvoiceInteractions
) {
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
                .background(Color.Transparent)
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
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            CalculationItem(
                                Resources.strings.subTotal,
                                state.calculations.subTotal.toString()
                            )
                            CalculationItem(
                                Resources.strings.totalTax,
                                state.calculations.totalTax.toString()
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            CalculationItem(
                                Resources.strings.netTotal,
                                state.calculations.netTotal.toString()
                            )
                            CalculationItem(
                                Resources.strings.fee,
                                state.calculations.fee.toString()
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            CalculationItem(
                                Resources.strings.amount,
                                state.calculations.amount.toString()
                            )
                            CalculationItem(
                                Resources.strings.totalPaid,
                                state.calculations.totalPaid.toString()
                            )
                            CalculationItem(
                                Resources.strings.remaining,
                                state.calculations.remaining.toString()
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.border(
                                BorderStroke(0.5.dp, Color.LightGray),
                                RoundedCornerShape(12.dp)
                            ).padding(16.dp)
                        ) {
                            CalculationItem(
                                Resources.strings.taken,
                                state.calculations.taken.toString()
                            )
                            CalculationItem(
                                Resources.strings.given,
                                state.calculations.given.toString()
                            )
                        }
                        StOutlinedButton(
                            title = "Update",
                            onClick = {},
                            modifier = Modifier
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DropDownTextField(
                            modifier = Modifier.width(120.dp),
                            options = state.discounts.map { it.toDropDownState() },
                            selectedItem = state.selectedDiscount.toDropDownState(),
                            label = Resources.strings.discount
                        ) { listener.onChooseDiscount(it) }

                        Row(modifier = Modifier) {
                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Percentage",
                                    color = Color.White,
                                    modifier = Modifier.width(80.dp)
                                )
                                BasicTextField(
                                    value = state.discountAmount.toString(),
                                    onValueChange = listener::onChangeDiscount,
                                    readOnly = state.selectedDiscount.name != "open",
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.width(120.dp)
                                        .border(
                                            BorderStroke(0.5.dp, Color.LightGray),
                                            RoundedCornerShape(12.dp)
                                        )
                                        .padding(vertical = 8.dp)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Discount Amount",
                                color = Color.White,
                                modifier = Modifier.width(80.dp)
                            )
                            Text(
                                text = ((state.discountAmount / 100) * state.calculations.subTotal).toString(),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(120.dp)
                                    .border(
                                        BorderStroke(0.5.dp, Color.LightGray),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }

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