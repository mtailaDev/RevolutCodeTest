package com.example.revolut.currencyrate.di

import com.example.revolut.currencyrate.data.CurrencyRateRepository
import com.example.revolut.currencyrate.domain.GetCurrencyRatesUseCase
import org.koin.dsl.module

val currencyRateModule = module {
    single { CurrencyRateRepository(networkDataServices = get()) }
    single { GetCurrencyRatesUseCase(repository = get()) }
}