package com.example.revolut.currencyrate.ui

import com.airbnb.mvrx.*
import com.example.revolut.currencyrate.domain.CurrencyRate
import com.example.revolut.currencyrate.domain.GetCurrencyRatesUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

const val initialRequest = "USD"

data class CurrencyRatesState(
    val ratesResult: Async<List<CurrencyRate>> = Uninitialized,
    val base: Async<String> = Success(initialRequest)
) : MvRxState

class CurrencyRatesViewModel(
    val initialState: CurrencyRatesState = CurrencyRatesState(),
    private val getCurrencyRatesUseCase: GetCurrencyRatesUseCase
) : BaseMvRxViewModel<CurrencyRatesState>
    (initialState = initialState, debugMode = BuildConfig.DEBUG) {

    val getRatesCompositeDisposable = CompositeDisposable().apply {
        disposeOnClear()
    }

    val getCurrencyCompositeDisposable = CompositeDisposable().apply {
        disposeOnClear()
    }

    init {
        getCurrencyRatesForBase(initialState.base.invoke()!!)
        asyncSubscribe(CurrencyRatesState::base, onSuccess = {
            getCurrency(it)
            refreshCurrency(it)
        })
    }

    fun getCurrencyRatesForBase(baseCurrency: String) {
        setState {
            copy(base = Success(baseCurrency))
        }
    }

    private fun refreshCurrency(baseCurrency: String) {
        getRatesCompositeDisposable.clear()
        Observable.interval(1, TimeUnit.SECONDS)
            .map {
                getCurrency(baseCurrency)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
            .addTo(getRatesCompositeDisposable)
    }

    private fun getCurrency(baseCurrency: String) {
        getCurrencyCompositeDisposable.clear()
        getCurrencyRatesUseCase.create(baseCurrency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .execute { copy(ratesResult = it) }
            .addTo(getCurrencyCompositeDisposable)
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