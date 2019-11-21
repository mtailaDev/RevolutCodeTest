package com.example.revolut.currencyrate.ui

import com.airbnb.epoxy.TypedEpoxyController
import com.example.revolut.currencyrate.domain.CurrencyRate

class ExchangeRateEpoxyController(
    private val onChangeRateListener: OnChangeRateListener,
    private val onCurrencyRateClickedListener: OnCurrencyRateClickedListener)
    : TypedEpoxyController<List<CurrencyRate>>() {

    var value = 0.0

    override fun buildModels(currencyRateList: List<CurrencyRate>) {
        currencyRateList.forEachIndexed { index, currencyRate ->
            currencyRate {
                id(currencyRate.currencyCode)
                base(index == 0)
                iconId(currencyRate.currencyAssociatedResourceId)
                currencyCode(currencyRate.currencyCode)
                valueConversion(currencyRate.conversionRate?.let { it * value } ?: value)
                onChangeRateListener(onChangeRateListener)
                onCurrencyRateClickedListener(onCurrencyRateClickedListener)
            }
        }
    }
}

interface OnChangeRateListener {
    fun onChangeRate(value: Double)
}

interface OnCurrencyRateClickedListener {
    fun onCurrencyRateClicked(currencyCode: String)
}