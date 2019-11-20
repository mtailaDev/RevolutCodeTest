package com.example.revolut.currencyrate.ui

import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.revolut.R
import com.example.revolut.common.KotlinEpoxyHolder
import com.example.revolut.common.ext.getCurrencyDisplayName
import com.example.revolut.common.ext.gone
import com.example.revolut.common.ext.visible

@EpoxyModelClass(layout = R.layout.list_item_currency_rate)
abstract class CurrencyRateEpoxyModel : EpoxyModelWithHolder<CurrencyRateEpoxyModelHolder>() {

    @EpoxyAttribute
    lateinit var currencyCode: String

    @EpoxyAttribute
    var base: Boolean = false

    override fun bind(holder: CurrencyRateEpoxyModelHolder) {
        with(holder) {
            // views
            val editTextCurrencyInput: EditText = root.findViewById(R.id.editTextCurrencyInput)
            val textCurrencyCode: TextView = root.findViewById(R.id.textCurrencyCode)
            val textCurrencyDisplayName: TextView = root.findViewById(R.id.textCurrencyDisplayName)
            val textCurrencyConversionValue: TextView =
                root.findViewById(R.id.textCurrencyConversionValue)

            textCurrencyCode.text = currencyCode
            textCurrencyDisplayName.text = currencyCode.getCurrencyDisplayName()

            if (base) {
                editTextCurrencyInput.visible()
                textCurrencyConversionValue.gone()
            } else {
                editTextCurrencyInput.gone()
                textCurrencyConversionValue.visible()
            }
        }
    }
}

class CurrencyRateEpoxyModelHolder : KotlinEpoxyHolder() {
    val root by bind<ConstraintLayout>(R.id.root)
}