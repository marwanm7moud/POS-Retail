package org.abapps.app.util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.abapps.app.util.Constants

fun getDateNow() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

fun getLanguageCodeByName(name: String): LanguageCode {
    return if (name == Constants.ARABIC) LanguageCode.AR else LanguageCode.EN
}