package com.example.revolut.currencyrate.domain

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized

data class CurrencyRatesState(val ratesResult: Async<List<CurrencyRate>> = Uninitialized) : MvRxState