package com.example.simplecachingexample.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class FavoriteCurrencyRepository @Inject constructor(
    private val db: CurrencyDatabase
) {
    private val favoriteCurrencyDao = db.favoriteCurrencyDao()


    val allFavoriteCurrencies: LiveData<List<FavoriteCurrencyDb>>
        get() {
            return favoriteCurrencyDao.getAllFavoriteCurrencies()
        }

    suspend fun insertFavoriteCurrency(favoriteCurrency: FavoriteCurrencyDb, onSuccess: () -> Unit) {
        favoriteCurrencyDao.insert(favoriteCurrency)
    }
    suspend fun deleteFavoriteCurrency(favoriteCurrency: FavoriteCurrencyDb, onSuccess: () -> Unit) {
        favoriteCurrencyDao.delete(favoriteCurrency)
    }
}