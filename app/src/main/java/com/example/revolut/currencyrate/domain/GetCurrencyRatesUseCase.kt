package com.example.revolut.currencyrate.domain

import com.example.revolut.currencyrate.data.CurrencyRateRepository
import io.reactivex.Single

class GetCurrencyRatesUseCase(
    private val repository: CurrencyRateRepository,
    private val toCurrencyRateUseCase: ToCurrencyRateUseCase
) {
    fun create(baseRateRequest: String): Single<List<CurrencyRate>> {
        return repository.getCurrencyRates(baseRateRequest)
            .map {
                toCurrencyRateUseCase.execute(it) }
    }
}