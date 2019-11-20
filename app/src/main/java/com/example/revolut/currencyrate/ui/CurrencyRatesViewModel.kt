package com.example.revolut.currencyrate.ui

import android.util.Log
import com.airbnb.mvrx.*
import com.example.revolut.currencyrate.domain.CurrencyRatesState
import com.example.revolut.currencyrate.domain.GetCurrencyRatesUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class CurrencyRatesViewModel(
    val initialState: CurrencyRatesState = CurrencyRatesState(),
    private val getCurrencyRatesUseCase: GetCurrencyRatesUseCase
) : BaseMvRxViewModel<CurrencyRatesState>
    (initialState = initialState, debugMode = BuildConfig.DEBUG) {

    init {
        Log.i("Initialized:", "CurrencyRatesViewModel")
    }

    fun getCurrencyRatesForBase(baseCurrency: String, packageName: String) {
        getCurrency(baseCurrency, packageName)
        refreshCurrency(baseCurrency, packageName)
    }

    private fun refreshCurrency(baseCurrency: String, packageName: String) {
        Observable.interval(1, TimeUnit.SECONDS)
            .map {
                getCurrency(baseCurrency, packageName)
            }
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    private fun getCurrency(baseCurrency: String, packageName: String) {
        getCurrencyRatesUseCase.create(baseCurrency, packageName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .execute { copy(ratesResult = it) }
    }


    companion object : MvRxViewModelFactory<CurrencyRatesViewModel, CurrencyRatesState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: CurrencyRatesState
        ): CurrencyRatesViewModel {
            val getCurrencyRatesUseCase = when (viewModelContext) {
                is FragmentViewModelContext -> viewModelContext.fragment.inject()
                is ActivityViewModelContext -> viewModelContext.activity.inject<GetCurrencyRatesUseCase>()
            }.value

            return CurrencyRatesViewModel(state, getCurrencyRatesUseCase)
        }
    }
}