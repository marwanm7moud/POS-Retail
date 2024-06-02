package org.abapps.app.util

import androidx.camera.core.ExperimentalGetImage
import androidx.compose.runtime.Composable
import org.abapps.app.PreviewViewComposable

@ExperimentalGetImage
@Composable
actual fun BarcodeReader(onBarcodeDetected:(String) -> Unit) {
    PreviewViewComposable(onBarcodeDetected)
}