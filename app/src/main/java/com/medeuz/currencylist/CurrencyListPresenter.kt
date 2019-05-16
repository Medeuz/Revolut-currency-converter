package com.medeuz.currencylist

import com.medeuz.currencylist.data.ApiHolder
import com.medeuz.currencylist.ui.CurrencyMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrencyListPresenter(private val view: CurrencyListView) {

    companion object {
        const val DEFAULT_SYMBOL = "EUR"
    }

    private var isLoaded = false

    fun loadCurrencyList(delay: Long, base: String = DEFAULT_SYMBOL): Disposable {
        return ApiHolder.fixerApi
            .getCurrencyRates(base)
            .delay(delay, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .repeat()
            .map { CurrencyMapper.transform(base, it) }
            .doOnSubscribe {
                if (!isLoaded) {
                    view.showLoading()
                }
            }
            .subscribe({
                view.showCurrencyList(it)
                if (!isLoaded) {
                    view.hideLoading()
                    isLoaded = true
                }
            }, {
                view.showError()
            })
    }
}