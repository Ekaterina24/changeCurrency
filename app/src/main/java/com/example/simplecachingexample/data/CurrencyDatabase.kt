package com.example.simplecachingexample.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CurrencyDb::class,
        FavoriteCurrencyDb::class,
        HistoryModel::class,
        AnalyticDb::class
    ], version = 1
)
//@TypeConverters(ValuesTypeConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
    abstract fun favoriteCurrencyDao(): FavoriteCurrencyDao
    abstract fun historyDao(): HistoryDao
    abstract fun analyticDao(): AnalyticDao
}