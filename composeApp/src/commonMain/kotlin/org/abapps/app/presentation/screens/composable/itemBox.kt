package org.abapps.app.presentation.screens.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun itemBox(content: String, modifier: Modifier = Modifier, textColor: Color = Color.White) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = content,
            color = textColor,
            maxLines = 1,
            textAlign = TextAlign.Center,
        )
    }
}