package com.example.simplecachingexample.data

import android.util.Log
import androidx.lifecycle.map
import androidx.room.withTransaction
import com.example.simplecachingexample.api.CurrencyApi
import com.example.simplecachingexample.util.networkBoundResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val api: CurrencyApi,
    private val db: CurrencyDatabase,
) {
    private val currencyDao = db.currencyDao()
    private val analyticDao = db.analyticDao()
//    private val favoriteList = db.favoriteCurrencyDao().getAllFavoriteCurrencies()

    fun getCurrencies() = networkBoundResource(
        query = {
            currencyDao.getAllCurrencies()
        },
        fetch = {
//            delay(2000)
//            api.getCurrencies().body()
        },
        saveFetchResult = { currencies ->
//            db.withTransaction {
//                val count = (currencyDao.getAllCurrenciesCount() == 0)
////                Log.d("MY_TAG", "count: $count")
//                val currentTimestamp = System.currentTimeMillis() / 1000
//                var currencyTimestamp: Long = 0
//                if (!count) {
//                    currencyTimestamp = currencyDao.getAllCurrencies().first().first().timestamp
//                }
//                val fetchTimestamp = currencies?.timestamp.toString().toLong()
//                if (((currentTimestamp - currencyTimestamp) >= 120) || count) {
//                    val dbCurrencies = currencyDao.getAllCurrencies()
//
////                    CoroutineScope(Dispatchers.IO).launch {
////                        dbCurrencies.collect() {
////                            it.map {
////                                val item = it
////
////                                Log.d("MY_TAG", "itemArray: ${analyticDao.getAllAnalyticItems().value}")
//////                                analyticDao.getAllAnalyticItems().value?.map {
//////                                    Log.d("MY_TAG", "itemArray: ${it.array}")
//////                                }
////
//////                                analyticDao.getAllAnalyticItems().observeForever {
//////                                    CoroutineScope(Dispatchers.IO).launch {
////////                                        val analyticItem = it[item.key.toInt()]
//////                                        it.forEach {
//////                                            if (item.key == it.key) {
//////                                                val updated = mutableListOf<String>()
//////                                                updated.addAll(it.array)
//////                                                Log.d("MY_TAG", "itemArray: ${it.array}")
//////                                                updated.add(item.value.toString())
//////                                                Log.d("MY_TAG", "itemValue: ${item.value}")
//////                                                Log.d("MY_TAG", "updated: ${it} ${updated}")
//////                                                analyticDao.update(AnalyticDb(it.key, updated))
//////                                            }
//////
//////
//////                                        }
////////                                        analyticDao.update(AnalyticDb(it.key, updated))
////////                                        Log.d("MY_TAG", "ArrayKey: ${ar}")
//////                                    }
//////                                }
////
//////                                analyticDao.insert(AnalyticDb(item.key, listOf(item.value.toString())))
////                            }
////                        }
////
////
////
//////
////                    }
//
//                    currencyDao.deleteAllCurrencies()
//                    var list: List<CurrencyDb>?
//                    if (count) {
//                        list = currencies?.rates?.toList()
//                            ?.map { (key, value) ->
//                                CurrencyDb(key, value, fetchTimestamp, false)
//                            }
//
//                        list?.map {
//                            analyticDao.insert(it.toAnalyticCurrency())
//                        }
//                    } else {
//                        list = currencies?.rates?.toList()
//                            ?.map { (key, value) ->
//                                    CurrencyDb(key, value, fetchTimestamp, false)
//                            }
//                        list?.map {
//                            analyticDao.insert(it.toAnalyticCurrency())
//                        }
//
//                        Log.d("MY_TAG", "list: $list")
//                    }
//
//                        if (list != null) {
//                            currencyDao.insertCurrencies(list)
//                        }
//                }
//            }
        }
    )
}