package com.example.revolut.currencyrate.ui

import com.airbnb.epoxy.TypedEpoxyController
import com.example.revolut.currencyrate.domain.CurrencyRate

class ExchangeRateEpoxyController : TypedEpoxyController<List<CurrencyRate>>() {
    override fun buildModels(currencyRateList: List<CurrencyRate>) {
        currencyRateList.forEachIndexed { index, currencyRate ->
            currencyRate {
                id(index)
                currencyCode(currencyRate.currencyCode)
                base(index == 0)
            }
        }
    }
}