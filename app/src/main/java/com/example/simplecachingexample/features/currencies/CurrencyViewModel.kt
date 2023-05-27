package com.example.simplecachingexample.features.currencies

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.example.simplecachingexample.data.*
import com.example.simplecachingexample.util.Resource
//import com.example.simplecachingexample.data.FavoriteCurrencyDb
//import com.example.simplecachingexample.data.FavoriteCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    repository: CurrencyRepository,
    db: CurrencyDatabase,
    favoriteRepository: FavoriteCurrencyRepository,
    historyRepository: HistoryRepository,
    analyticRepository: AnalyticRepository
): ViewModel() {
    val currencies = repository.getCurrencies().asLiveData()
    val favoriteCurrencies = favoriteRepository.allFavoriteCurrencies
    val analyticCurrencies = analyticRepository.allAnalyticItems
    val historyRepository = historyRepository
    val analyticRepository = analyticRepository
    val favoriteRepository = favoriteRepository
    val historyItems = historyRepository.allHistoryItems

    val db = db

//    @SuppressLint("CheckResult")
//    fun installFavorite(): LiveData<Resource<List<CurrencyDb>>> {
//        CoroutineScope(Dispatchers.IO).launch {
//            currencies.map { list ->
//                Log.d("MY_TAG", "installFavorite: $list")
////                list.data?.forEach { i ->
////                    favoriteCurrencies.map { favorite ->
//////                        CoroutineScope(Dispatchers.IO).launch {
//////                            favorite.forEach { k ->
//////                                if (i.value == k.value) {
//////                                    val item = i
//////                                    val updated = item.copy(isFavorite = true)
//////                                    db.currencyDao().update(updated)
////////                                i.isFavorite === true
//////                                    db.currencyDao().getAllCurrencies()
//////                                }
//////                            }
//////                        }
////                        Log.d("MY_TAG", "listWithFavorite: $favorite")
////                    }
////                    Log.d("MY_TAG", "listWithFavorite: $list")
////                }
//
//            }
//        }
//        return currencies
//    }

    fun onLikeClicked(currency: CurrencyDb) {
        CoroutineScope(Dispatchers.IO).launch {
            val item = currency.toFavoriteCurrency()
            val updatedCurrency = currency.copy(isFavorite = !currency.isFavorite)
            if (updatedCurrency.isFavorite) {
                db.favoriteCurrencyDao().insert(item)
            } else {
                db.favoriteCurrencyDao().delete(item)
            }
            db.currencyDao().update(updatedCurrency)
            db.currencyDao().getAllCurrencies()
        }
    }

    fun insertHistoryItem(firstKey: String, firstValue: Double, secondKey: String, secondValue: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentTimestamp = System.currentTimeMillis() / 1000
            val item = HistoryModel(firstKey, firstValue, secondKey, secondValue, currentTimestamp)
            historyRepository.insertHistory(item)
        }
    }

//    fun insertAnalyticItem(analyticItem: AnalyticDb) {
//        viewModelScope.launch(Dispatchers.IO) {
//            analyticRepository.insertAnalyticCurrency(analyticItem)
//        }
//    }
//
//    fun updateAnalyticItem(analyticItem: AnalyticDb) {
//        viewModelScope.launch(Dispatchers.IO) {
//            analyticRepository.updateAnalyticCurrency(analyticItem)
//        }
//    }




}