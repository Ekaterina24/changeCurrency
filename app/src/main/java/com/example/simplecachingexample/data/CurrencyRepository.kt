package com.example.simplecachingexample.data

import android.util.Log
import androidx.room.util.query
import androidx.room.withTransaction
import com.example.simplecachingexample.api.CurrencyApi
import com.example.simplecachingexample.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
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
            api.getCurrencies(apikey = "lv2voBFjoTPkjStpdPdlH7N36jv0UyQ3").body()
        },
        saveFetchResult = { currencies ->
            db.withTransaction {
                val count = (currencyDao.getAllCurrenciesCount() == 0)
                Log.d("MY_TAG", "count: $count")
                val currentTimestamp = System.currentTimeMillis() / 1000
                var currencyTimestamp: Long = 0
                if (!count) {
                    currencyTimestamp = currencyDao.getAllCurrencies().first().first().timestamp
                }
                val fetchTimestamp = currencies?.timestamp.toString().toLong()
//                Log.d("MY_TAG", "time: ${currentTimestamp - currencyTimestamp}")
                if (((currentTimestamp - currencyTimestamp) >= 300) || count) {
                currencyDao.deleteAllCurrencies()
                val list: List<CurrencyDb>? = currencies?.rates?.toList()
                    ?.map { (key, value) -> CurrencyDb(key, value, fetchTimestamp, false) }
                if (list != null) {
                    currencyDao.insertCurrencies(list)
                    }
                }
            }
        }
    )
}