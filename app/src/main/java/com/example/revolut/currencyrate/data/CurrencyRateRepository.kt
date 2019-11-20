package com.example.revolut.currencyrate.data

import com.example.revolut.core.ApiServices

class CurrencyRateRepository(private val networkDataServices: ApiServices) {
    fun getCurrencyRates(baseCurrency: String) = networkDataServices.getCurrencyRates(baseCurrency)
}