package org.abapps.app.presentation.screens.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.abapps.app.presentation.screens.composable.AppThreeDotLoadingIndicator

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize().background(Color(0xFF11142D)),
        contentAlignment = Alignment.Center
    ) {
        AppThreeDotLoadingIndicator()
    }
}