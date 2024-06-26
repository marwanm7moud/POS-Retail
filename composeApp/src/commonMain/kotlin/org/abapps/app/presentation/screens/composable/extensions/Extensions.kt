package org.abapps.app.presentation.screens.composable.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPx(): Float = with(LocalDensity.current) { toPx() }

//@Composable
//fun Dp.toResponsive(): Dp = (this.toPx() * LocalDensity.current.density).dp

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color, isVisible: Boolean = true) = composed(
    factory = {
        if (isVisible) {
            val density = LocalDensity.current
            val strokeWidthPx = density.run { strokeWidth.toPx() }

            Modifier.drawBehind {
                val width = size.width
                val height = size.height - strokeWidthPx / 2

                drawLine(
                    color = color,
                    start = Offset(x = 0f, y = height),
                    end = Offset(x = width, y = height),
                    strokeWidth = strokeWidthPx
                )
            }
        } else this
    }
)