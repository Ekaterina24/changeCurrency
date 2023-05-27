package com.example.simplecachingexample.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    db: CurrencyDatabase
) {
    private val historyDao = db.historyDao()

    val allHistoryItems: LiveData<List<HistoryModel>>
        get() {
            return historyDao.getAllHistoryItems()
        }

    suspend fun insertHistory(item: HistoryModel) {
        historyDao.insert(item)
    }
}