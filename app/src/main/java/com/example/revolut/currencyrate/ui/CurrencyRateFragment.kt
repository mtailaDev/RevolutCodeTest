package com.example.revolut.currencyrate.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.*
import com.example.revolut.R
import com.example.revolut.common.ext.scrollPercentage

class CurrencyRateFragment : BaseMvRxFragment(), OnChangeRateListener{

    private val getCurrencyRatesViewModel: CurrencyRatesViewModel by fragmentViewModel()

    // views
    private lateinit var root: View
    private lateinit var textRatesTitle: TextView
    private lateinit var recyclerViewRates: EpoxyRecyclerView

    // variables
    private val exchangeRateEpoxyController : ExchangeRateEpoxyController by lazy {
        ExchangeRateEpoxyController(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_currency_rates, container, false)
        recyclerViewRates = root.findViewById(R.id.recyclerViewRates)
        textRatesTitle = root.findViewById(R.id.textRatesTitle)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupRecyclerViewScrollListener()
    }

    override fun onChangeRate(value: Double) {
        exchangeRateEpoxyController.value = value
    }

    override fun onResume() {
        super.onResume()
        getCurrencyRatesViewModel.getCurrencyRatesForBase("GBP", requireActivity().packageName)
    }

    override fun invalidate(): Unit = withState(getCurrencyRatesViewModel) { state ->
        when (state.ratesResult) {
            is Loading -> {
                Log.i("Currency Rates", "Loading")
            }
            is Success -> {
                Log.i("Currency Rates", "Success")
                exchangeRateEpoxyController.setData(state.ratesResult.invoke()!!)
            }
            is Fail -> {
                Log.i("Currency Rates", "Fail")
            }
        }
    }

    private fun setupRecyclerViewScrollListener() {
        recyclerViewRates.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                textRatesTitle.elevation = recyclerView.scrollPercentage()
            }
        })
    }

    private fun setupRecyclerView() {
        recyclerViewRates.adapter = exchangeRateEpoxyController.adapter
    }

}