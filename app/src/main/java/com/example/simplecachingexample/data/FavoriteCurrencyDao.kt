package com.example.simplecachingexample.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(currencyFavoriteItem: FavoriteCurrencyDb)

    @Delete
    suspend fun delete(currencyFavoriteItem: FavoriteCurrencyDb)

    @Query("SELECT * FROM favoriteCurrenciesDb")
    fun getAllFavoriteCurrencies(): LiveData<List<FavoriteCurrencyDb>>
}