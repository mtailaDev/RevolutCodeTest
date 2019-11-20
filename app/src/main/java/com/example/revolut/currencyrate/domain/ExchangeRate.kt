package com.example.revolut.currencyrate.domain

data class ExchangeRate(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
) {
    fun toCurrencyRate(): List<CurrencyRate> {
        val currencyRateList = mutableListOf<CurrencyRate>()
        currencyRateList.add(CurrencyRate(currencyCode = base))
        rates.forEach {
            currencyRateList.add(CurrencyRate(it.key, it.value))
        }
        return currencyRateList
    }
}
// todo add function to get resource icons

data class CurrencyRate(
    val currencyCode: String,
    val conversionRate: Double? = null,
    var currencyAssociatedResourceId: Int = 0
)