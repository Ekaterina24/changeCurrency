package com.example.simplecachingexample.features.currencies

import androidx.lifecycle.*
import com.example.simplecachingexample.data.CurrencyDatabase
import com.example.simplecachingexample.data.CurrencyDb
import com.example.simplecachingexample.data.CurrencyRepository
//import com.example.simplecachingexample.data.FavoriteCurrencyDb
import com.example.simplecachingexample.data.FavoriteCurrencyRepository
//import com.example.simplecachingexample.data.FavoriteCurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    repository: CurrencyRepository,
    db: CurrencyDatabase,
    favoriteRepository: FavoriteCurrencyRepository
): ViewModel() {
    val currencies = repository.getCurrencies().asLiveData()
    val favoriteCurrencies = favoriteRepository.allFavoriteCurrencies
    val db = db
}