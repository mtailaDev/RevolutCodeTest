package com.example.revolut.currencyrate.domain

import com.example.revolut.currencyrate.data.CurrencyRateRepository
import io.reactivex.Single

class GetCurrencyRatesUseCase(private val repository: CurrencyRateRepository) {
    fun execute(baseRateRequest: String): Single<String> {
        return repository.getCurrencyRates(baseRateRequest)
    }
}