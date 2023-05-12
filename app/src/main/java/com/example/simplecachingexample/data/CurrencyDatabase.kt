package com.example.simplecachingexample.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CurrencyDb::class,
        FavoriteCurrencyDb::class
    ], version = 1
)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun favoriteCurrencyDao(): FavoriteCurrencyDao
}