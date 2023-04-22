package com.example.simplecachingexample.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currenciesDb")
data class CurrencyDb(
    @PrimaryKey(autoGenerate = false)  val key: String,
    val value: Double
)