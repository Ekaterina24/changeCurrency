package com.example.simplecachingexample.features.currencies

import android.util.Log
import androidx.lifecycle.*
import com.example.simplecachingexample.data.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    repository: CurrencyRepository
): ViewModel() {
    val currencies = repository.getCurrencies().asLiveData()


    init {
        Log.d("viewModel", "onCreate:")
    }

//    private val restaurantsLiveData = MutableLiveData<List<Restaurant>>()
//    val restaurants: LiveData<List<Restaurant>> = restaurantsLiveData
//
//    init {
//        viewModelScope.launch {
//            val restaurants = api.getRestaurants()
//            delay(2000)
//            restaurantsLiveData.value = restaurants
//        }
//    }
}