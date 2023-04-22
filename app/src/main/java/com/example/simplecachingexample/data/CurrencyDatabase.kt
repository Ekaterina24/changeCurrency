package com.example.simplecachingexample.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CurrencyDb::class], version = 1)
abstract class CurrencyDatabase: RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}