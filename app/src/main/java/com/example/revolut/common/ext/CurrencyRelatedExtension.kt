package com.example.revolut.common.ext

import android.util.Log
import java.util.*

const val USD_CURRENCY_CODE = "USD"
const val EUR_CURRENCY_CODE = "EUR"

const val USD_country_code = "us"
const val EUR_country_code = "eu"

fun String.getCurrencyDisplayName() = Currency.getInstance(this).displayName

fun mapCurrenciesCodesToCountryCodes(): HashMap<String, String> {
    val hashMap = HashMap<String, String>()
    Locale.getAvailableLocales().forEach { locale ->
        try {
            hashMap[Currency.getInstance(locale).currencyCode] = when {
                Currency.getInstance(locale).currencyCode == USD_CURRENCY_CODE -> {
                     USD_country_code
                }
                Currency.getInstance(locale).currencyCode == EUR_CURRENCY_CODE -> {
                    EUR_country_code
                }
                else -> {
                    locale.country.toLowerCase(locale)
                }
            }
        } catch (e: Exception) {
            Log.e("Currency Code error:", e.localizedMessage)
        }
    }
    return hashMap
}