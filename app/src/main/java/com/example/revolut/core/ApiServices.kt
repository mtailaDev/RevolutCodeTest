package com.example.revolut.core

import com.example.revolut.currencyrate.domain.ExchangeRate
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("/latest")
    fun getCurrencyRates(@Query("base") baseCurrency: String): Single<ExchangeRate>
}
