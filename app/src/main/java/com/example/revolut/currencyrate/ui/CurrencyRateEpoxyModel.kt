package com.example.revolut.currencyrate.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.example.revolut.R
import com.example.revolut.common.ui.KotlinEpoxyHolder
import com.example.revolut.common.ext.getCurrencyDisplayName
import com.example.revolut.common.ext.gone
import com.example.revolut.common.ext.visible
import de.hdodenhof.circleimageview.CircleImageView

@EpoxyModelClass(layout = R.layout.list_item_currency_rate)
abstract class CurrencyRateEpoxyModel : EpoxyModelWithHolder<CurrencyRateEpoxyModelHolder>() {

    @EpoxyAttribute
    lateinit var onChangeRateListener: OnChangeRateListener

    @EpoxyAttribute
    var iconId: Int = 0

    @EpoxyAttribute
    lateinit var currencyCode: String

    @EpoxyAttribute
    var base: Boolean = false

    @EpoxyAttribute
    var valueConversion: Double = 0.0

    override fun bind(holder: CurrencyRateEpoxyModelHolder) {
        with(holder) {
            // views
            val editTextCurrencyInput: EditText = root.findViewById(R.id.editTextCurrencyInput)
            val textCurrencyCode: TextView = root.findViewById(R.id.textCurrencyCode)
            val textCurrencyDisplayName: TextView = root.findViewById(R.id.textCurrencyDisplayName)
            val textCurrencyConversionValue: TextView = root.findViewById(R.id.textCurrencyConversionValue)
            val imageCurrencyIcon: ImageView = root.findViewById(R.id.imageCurrencyIcon)

            textCurrencyCode.text = currencyCode
            textCurrencyDisplayName.text = currencyCode.getCurrencyDisplayName()
            Glide.with(root.context).load(iconId).circleCrop().into(imageCurrencyIcon)


//            if (iconId > 0){
//                imageCurrencyIcon.setImageResource(iconId)
//            }

            if (base) {
                textCurrencyConversionValue.gone()
                editTextCurrencyInput.visible()
                setTextWatcher(editTextCurrencyInput)
                editTextCurrencyInput.setText("$valueConversion")
            } else {
                editTextCurrencyInput.gone()
                textCurrencyConversionValue.visible()
                textCurrencyConversionValue.text = String.format("%.2f", valueConversion)
            }
        }
    }

    private fun setTextWatcher(editTextCurrencyInput: EditText) {
        editTextCurrencyInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty()) {
                    onChangeRateListener.onChangeRate(editable.toString().toDouble())
                } else {
                    onChangeRateListener.onChangeRate(0.0)
                }
            }
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }
}

class CurrencyRateEpoxyModelHolder : KotlinEpoxyHolder() {
    val root by bind<ConstraintLayout>(R.id.root)
}