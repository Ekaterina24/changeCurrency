package com.example.simplecachingexample.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getAllHistoryItems(): LiveData<List<HistoryModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(historyItem: HistoryModel)
}