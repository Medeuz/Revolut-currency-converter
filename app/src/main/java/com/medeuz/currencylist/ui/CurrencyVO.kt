package com.medeuz.currencylist.ui

import androidx.annotation.DrawableRes

data class CurrencyVO(
    val code: String,
    val title: String,
    @DrawableRes val image: Int,
    var value: Double
)