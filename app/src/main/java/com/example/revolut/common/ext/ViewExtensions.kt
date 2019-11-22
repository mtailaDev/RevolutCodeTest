package com.example.revolut.common.ext

import android.view.View
import androidx.recyclerview.widget.RecyclerView

const val scrollPercentageMultiplier = 8f

fun RecyclerView.scrollPercentage(): Float {
    val scrollPercentage = (scrollPercentageMultiplier * computeVerticalScrollOffset() /
            (computeVerticalScrollRange() - computeVerticalScrollExtent()))
    return if (scrollPercentage.isNaN()) 0f else scrollPercentage
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}