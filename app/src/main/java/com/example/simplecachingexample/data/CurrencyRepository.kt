package com.example.simplecachingexample.data

import androidx.room.withTransaction
import com.example.simplecachingexample.api.CurrencyApi
import com.example.simplecachingexample.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val api: CurrencyApi,
    private val db: CurrencyDatabase
) {
    private val currencyDao = db.currencyDao()

    fun getCurrencies() = networkBoundResource(
        query = {
            currencyDao.getAllCurrencies()
        },
        fetch = {
            delay(2000)
            api.getCurrencies(apikey = "lv2voBFjoTPkjStpdPdlH7N36jv0UyQ3").body()?.rates
        },
        saveFetchResult = { currencies ->
            db.withTransaction {
                currencyDao.deleteAllCurrencies()
                val list : List<CurrencyDb>? = currencies?.toList()?.map { (key, value) -> CurrencyDb(key, value) }
                if (list != null) {
                    currencyDao.insertCurrencies(list)
                }

            }
        }
    )
}