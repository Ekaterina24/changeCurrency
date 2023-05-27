package com.example.simplecachingexample.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class AnalyticRepository @Inject constructor(
    db: CurrencyDatabase
) {
    private val analyticCurrencyDao = db.analyticDao()

    val allAnalyticItems: LiveData<List<AnalyticDb>>
        get() {
            return analyticCurrencyDao.getAllAnalyticItems()
        }

    suspend fun insertAnalyticCurrency(analyticCurrency: AnalyticDb) {
        analyticCurrencyDao.insert(analyticCurrency)
    }

    suspend fun updateAnalyticCurrency(analyticCurrency: AnalyticDb) {
        analyticCurrencyDao.update(analyticCurrency)
    }
}