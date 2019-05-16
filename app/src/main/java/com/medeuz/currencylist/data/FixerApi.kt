package com.medeuz.currencylist.data

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FixerApi {
    @GET("latest")
    fun getCurrencyRates(@Query("base") base: String): Single<CurrencyRatesDTO>
}

// to be good, here should be fine dagger2 injection
object ApiHolder {
    private const val BASE_URL = "https://revolut.duckdns.org/"

    val fixerApi: FixerApi by lazy {
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(BASE_URL)
                .build()
                .create(FixerApi::class.java)
    }
}