package org.abapps.app.util

import androidx.compose.runtime.Composable


@Composable
expect fun BarcodeReader(onBarcodeDetected:(String) -> Unit)