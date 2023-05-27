package com.example.simplecachingexample.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AnalyticDao {

    @Query("SELECT * FROM analyticDb")
    fun getAllAnalyticItems(): LiveData<List<AnalyticDb>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(analyticItem: AnalyticDb)

    @Update
    suspend fun update(analyticItem: AnalyticDb)
}