package com.example.revolut.currencyrate.domain

import com.example.revolut.RevolutApp

class ToCurrencyRateUseCase(private val currencyAndCountryCodeMap: HashMap<String, String>) {
    fun execute(exchangeRate: ExchangeRate, packageName: String): List<CurrencyRate> {
        val currencyRateList = mutableListOf<CurrencyRate>()
        currencyRateList.add(
            CurrencyRate(
                currencyCode = exchangeRate.base,
                conversionRate = null,
                currencyAssociatedResourceId = getResourceId(exchangeRate.base, packageName)
            )
        )
        exchangeRate.rates.forEach {
            currencyRateList.add(
                CurrencyRate(
                    currencyCode = it.key,
                    conversionRate = it.value,
                    currencyAssociatedResourceId = getResourceId(it.key, packageName)
                )
            )
        }
        return currencyRateList
    }

    private fun getResourceId(key: String, packageName: String) =
        RevolutApp.instance.resources.getIdentifier(
            currencyAndCountryCodeMap[key],
            "drawable",
            packageName
        )
}