package org.abapps.app.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp

@Composable
fun calculateBiggestWidthOnEveryRow(list: List<String>): Int {
    var biggestwidth by remember { mutableStateOf(0) }
    for (string in list) {
        Box(
            modifier = Modifier
                .padding(4.dp)
                .onGloballyPositioned { coordinates ->
                    if (coordinates.size.width >= biggestwidth)
                        biggestwidth = coordinates.size.width
                }

        ) {
            Text(text = string, color = Color.White)
        }
    }
    return biggestwidth
}