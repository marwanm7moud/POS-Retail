package org.abapps.app.util

import androidx.compose.ui.unit.LayoutDirection
import org.abapps.app.resource.strings.IStringResources
import org.abapps.app.resource.strings.arabic.Arabic
import org.abapps.app.resource.strings.english.English

object LocalizationManager {

    fun getStringResources(languageCode: LanguageCode): IStringResources {
        return when (languageCode) {
            LanguageCode.EN -> English()
            LanguageCode.AR -> Arabic()
        }
    }

    fun getLayoutDirection(languageCode: LanguageCode): LayoutDirection {
        return when (languageCode) {
            LanguageCode.EN -> LayoutDirection.Ltr
            else -> LayoutDirection.Rtl
        }
    }
}