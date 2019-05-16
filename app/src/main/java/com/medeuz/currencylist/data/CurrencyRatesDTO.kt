package com.medeuz.currencylist.data

data class CurrencyRatesDTO(
        val base: String,
        val date: String,
        val rates: Map<String, Double>
)