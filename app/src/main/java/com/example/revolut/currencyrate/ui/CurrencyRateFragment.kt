package com.example.revolut.currencyrate.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.revolut.R
import com.example.revolut.common.ext.scrollPercentage
import com.example.revolut.currencyrate.domain.CurrencyRate
import com.example.revolut.currencyrate.ui.ExchangeRateEpoxyController
import com.example.revolut.currencyrate.ui.OnChangeRateListener

class CurrencyRateFragment : Fragment(), OnChangeRateListener{

    // views
    private lateinit var root: View
    private lateinit var textRatesTitle: TextView
    private lateinit var recyclerViewRates: EpoxyRecyclerView

    // variables
    private val exchangeRateEpoxyController : ExchangeRateEpoxyController by lazy {
        ExchangeRateEpoxyController(this)
    }

    val list = getStubData()

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
        exchangeRateEpoxyController.setData(list)
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
        exchangeRateEpoxyController.setData(list)
    }

    private fun getStubData() = listOf(
        CurrencyRate("BGN", conversionRate = null),
        CurrencyRate("BRL"),
        CurrencyRate("CAD"),
        CurrencyRate("CHF"),
        CurrencyRate("BRL"),
        CurrencyRate("GBP"),
        CurrencyRate("BGN"),
        CurrencyRate("BRL"),
        CurrencyRate("CAD"),
        CurrencyRate("CHF"),
        CurrencyRate("BRL"),
        CurrencyRate("GBP"),
        CurrencyRate("EUR")
    )
}