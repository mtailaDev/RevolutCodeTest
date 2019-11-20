package com.example.revolut.common.ext

import java.util.*

fun String.getCurrencyDisplayName() = Currency.getInstance(this).displayName