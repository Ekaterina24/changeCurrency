package com.example.simplecachingexample.api

import com.example.simplecachingexample.data.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CurrencyApi {

    companion object {
        const val BASE_URL = "https://api.apilayer.com/fixer/"
    }

    @GET("latest")
    suspend fun getCurrencies(@Header("apikey") apikey: String): Response<Currency>
}