package com.medeuz.currencylist.ui

import com.medeuz.currencylist.R
import com.medeuz.currencylist.data.CurrencyRatesDTO

object CurrencyMapper {
    fun transform(base: String, item: CurrencyRatesDTO): List<CurrencyVO> {
        val result = ArrayList<CurrencyVO>()
        if (item.rates[base] == null) (
                CurrencyVO(
                    code = base,
                    value = 1.0,
                    image = R.mipmap.ic_launcher, // api doesnt give image url
                    title = "api doest give enough data"
                ).also { result.add(it) }
                )
        item.rates.map {
            CurrencyVO(
                code = it.key,
                value = it.value,
                image = R.mipmap.ic_launcher, // api doesnt give image url
                title = "api doest give enough data"
            )
        }.also { result.addAll(it) }

        return result
    }
}