package com.medeuz.currencylist

import com.medeuz.currencylist.ui.CurrencyVO

interface CurrencyListView {

    companion object {
        const val CURRENCY_UPDATE_DELAY_SECONDS = 1L
    }

    fun showError()
    fun showCurrencyList(items: List<CurrencyVO>)
    fun showLoading()
    fun hideLoading()
}