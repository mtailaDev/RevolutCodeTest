package com.example.revolut.currencyrate.ui

import android.util.Log
import android.view.inputmethod.EditorInfo
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
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

@EpoxyModelClass(layout = R.layout.list_item_currency_rate)
abstract class CurrencyRateEpoxyModel : EpoxyModelWithHolder<CurrencyRateEpoxyModelHolder>() {

    @EpoxyAttribute
    lateinit var onCurrencyRateClickedListener: OnCurrencyRateClickedListener

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
            val compositeDisposable = CompositeDisposable()

            // views
            val editTextCurrencyInput: EditText = root.findViewById(R.id.editTextCurrencyInput)
            val textCurrencyCode: TextView = root.findViewById(R.id.textCurrencyCode)
            val textCurrencyDisplayName: TextView = root.findViewById(R.id.textCurrencyDisplayName)
            val textCurrencyConversionValue: TextView =
                root.findViewById(R.id.textCurrencyConversionValue)
            val imageCurrencyIcon: ImageView = root.findViewById(R.id.imageCurrencyIcon)
            editTextCurrencyInput.imeOptions = EditorInfo.IME_ACTION_DONE

            textCurrencyCode.text = currencyCode
            textCurrencyDisplayName.text = currencyCode.getCurrencyDisplayName()
            if (iconId > 0) {
                Glide.with(root.context).load(iconId).circleCrop().into(imageCurrencyIcon)
            }
            if (base) {
                textCurrencyConversionValue.gone()
                editTextCurrencyInput.visible()
                setTextWatcher(editTextCurrencyInput, compositeDisposable)
                editTextCurrencyInput.setText(
                    String.format("%.0f", valueConversion),
                    TextView.BufferType.EDITABLE
                )
                editTextCurrencyInput.setSelection(String.format("%.0f", valueConversion).length)
            } else {
                editTextCurrencyInput.gone()
                textCurrencyConversionValue.visible()
                textCurrencyConversionValue.text = String.format("%.2f", valueConversion)
                root.setOnClickListener {
                    onCurrencyRateClickedListener.onCurrencyRateClicked(currencyCode)
                }
            }
        }
    }

    private fun setTextWatcher(
        editTextCurrencyInput: EditText,
        compositeDisposable: CompositeDisposable
    ) {
        editTextCurrencyInput.requestFocus()
        compositeDisposable.clear()
        compositeDisposable.add(
            editTextCurrencyInput
                .textChanges()
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribe({
                    if (it.isNotEmpty()) {
                        onChangeRateListener.onChangeRate(it.toString().toDouble())
                    }
                }, {
                    Log.e("Base input error", it.toString())
                })
        )
    }
}

class CurrencyRateEpoxyModelHolder : KotlinEpoxyHolder() {
    val root by bind<ConstraintLayout>(R.id.root)
}