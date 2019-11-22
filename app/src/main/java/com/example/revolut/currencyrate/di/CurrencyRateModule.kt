package com.example.revolut.currencyrate.di

import com.example.revolut.common.ext.mapCurrenciesCodesToCountryCodes
import com.example.revolut.currencyrate.data.CurrencyRateRepository
import com.example.revolut.currencyrate.domain.GetCurrencyRatesUseCase
import com.example.revolut.currencyrate.domain.ToCurrencyRateUseCase
import com.example.revolut.currencyrate.ui.CurrencyRatesState
import com.example.revolut.currencyrate.ui.CurrencyRatesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val currencyRateModule = module {
    single { CurrencyRateRepository(networkDataServices = get()) }
    single { ToCurrencyRateUseCase(currencyAndCountryCodeMap = mapCurrenciesCodesToCountryCodes()) }
    single { GetCurrencyRatesUseCase(repository = get(), toCurrencyRateUseCase = get()) }
    viewModel {
        CurrencyRatesViewModel(
            initialState = CurrencyRatesState(),
            getCurrencyRatesUseCase = get()
        )
    }
}