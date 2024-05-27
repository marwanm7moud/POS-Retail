package org.abapps.app.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.sp

@Composable
fun calculateBiggestWidthOnEveryRow(list: List<String>): Int {
    var maxWidth by remember { mutableStateOf(0) }

    SubcomposeLayout { constraints ->
        val measurables = list.toSet().toList().map { text ->
            subcompose(text) {
                Text(text = text, fontSize = 16.sp)
            }.first()
        }

        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)
            maxWidth = maxOf(maxWidth, placeable.width)
            placeable
        }
        layout(0, 0) {

        }
    }

    return maxWidth
}