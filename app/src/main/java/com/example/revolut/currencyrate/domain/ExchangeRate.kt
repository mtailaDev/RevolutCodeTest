package com.example.revolut.currencyrate.domain

data class ExchangeRate(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

data class CurrencyRate(
    val currencyCode: String,
    val conversionRate: Double?,
    var currencyAssociatedResourceId: Int = 0
)