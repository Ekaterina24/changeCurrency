package com.example.simplecachingexample.api

import com.example.simplecachingexample.data.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CurrencyApi {

    companion object {
        const val BASE_URL = "http://data.fixer.io/api/"
    }

    @GET("latest?access_key=700ee9ea3f1f6d2a7cb2fe6ac4c407ce")
    suspend fun getCurrencies(): Response<Currency>
}