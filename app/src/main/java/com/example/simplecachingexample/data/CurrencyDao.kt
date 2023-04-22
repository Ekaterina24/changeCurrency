package com.example.simplecachingexample.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currenciesDb")
    fun getAllCurrencies(): Flow<List<CurrencyDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currency: List<CurrencyDb>)

    @Query("DELETE FROM currenciesDb")
    suspend fun deleteAllCurrencies()
}