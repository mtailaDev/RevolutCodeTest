package com.example.revolut.common.ext

import android.util.Log
import java.util.*

fun String.getCurrencyDisplayName() = Currency.getInstance(this).displayName

fun mapCurrenciesCodesToCountryCodes(): HashMap<String, String> {
    val hashMap = HashMap<String, String>()
    Locale.getAvailableLocales().forEach { locale ->
        locale.unicodeLocaleAttributes
        try {
            hashMap[Currency.getInstance(locale).currencyCode] = locale.country.toLowerCase(locale)
        } catch (e: Exception) {
            Log.e("Currency Code error:", e.localizedMessage)
        }
    }
    return hashMap
}