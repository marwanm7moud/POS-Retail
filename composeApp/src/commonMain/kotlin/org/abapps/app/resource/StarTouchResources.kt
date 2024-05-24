package org.abapps.app.resource

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalLayoutDirection
import org.abapps.app.resource.strings.IStringResources
import com.beepbeep.designSystem.ui.theme.StTheme
import org.abapps.app.util.LanguageCode
import org.abapps.app.util.LocalizationManager
import org.abapps.app.util.setInsetsController


private val localStringResources = staticCompositionLocalOf<IStringResources> {
    error("CompositionLocal IStringResources not present")
}

@Composable
fun StarTouchTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    languageCode: LanguageCode,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        localStringResources provides LocalizationManager.getStringResources(languageCode),
        LocalLayoutDirection provides LocalizationManager.getLayoutDirection(languageCode),
    ) {
        StTheme(useDarkTheme = useDarkTheme) {
            setInsetsController(useDarkTheme)
            content()
        }
    }
}


object Resources {
    val strings: IStringResources
        @Composable
        @ReadOnlyComposable
        get() = localStringResources.current
}