package org.abapps.app.presentation.screens.invoiceScreen.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.composable.StTextField
import org.abapps.app.presentation.screens.composable.DropDownState
import org.abapps.app.presentation.screens.composable.DropDownTextField

@Composable
fun BrandonCard() {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp , vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                StTextField(
                    label = "INVC No",
                    text = "",
                    onValueChange = {},
                )
            }
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = listOf(
                    DropDownState(0, "gg"),
                    DropDownState(1, "gg"),
                    DropDownState(2, "gg")
                ),
                selectedItem = DropDownState(0, "gg"),
                label = "INVC Type"
            ) {

            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                StTextField(
                    label = "Customer",
                    text = "",
                    onValueChange = {},
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                StTextField(
                    label = "Status",
                    text = "Regular",
                    onValueChange = {},
                    readOnly = true
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(modifier = Modifier.weight(1f)) {
                StTextField(
                    label = "Source Type",
                    text = "Invoice",
                    onValueChange = {},
                    readOnly = true
                )
            }
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = listOf(
                    DropDownState(0, "gg"),
                    DropDownState(1, "gg"),
                    DropDownState(2, "gg")
                ),
                selectedItem = DropDownState(0, "gg"),
                label = "Sales Order"
            ) {

            }
        }
        StTextField(
            label = "Comment",
            textFieldModifier = Modifier.fillMaxWidth().height(96.dp),
            text = "",
            onValueChange = {},
            readOnly = true
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = listOf(
                    DropDownState(0, "gg"),
                    DropDownState(1, "gg"),
                    DropDownState(2, "gg")
                ),
                selectedItem = DropDownState(0, "gg"),
                label = "Store"
            ) {}
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = listOf(
                    DropDownState(0, "gg"),
                    DropDownState(1, "gg"),
                    DropDownState(2, "gg")
                ),
                selectedItem = DropDownState(0, "gg"),
                label = "Cashier"
            ) {}
            DropDownTextField(
                modifier = Modifier.weight(1f),
                options = listOf(
                    DropDownState(0, "gg"),
                    DropDownState(1, "gg"),
                    DropDownState(2, "gg")
                ),
                selectedItem = DropDownState(0, "gg"),
                label = "Sales Person"
            ) {}
        }
    }
}