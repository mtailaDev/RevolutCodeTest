package com.example.revolut.core

import io.reactivex.Single

interface ApiServices {
    fun getCurrencyRates(baseCurrency: String) : Single<String>
}
