package com.example.simplecachingexample.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favoriteCurrenciesDb")
data class FavoriteCurrencyDb(
    @PrimaryKey(autoGenerate = false) val key: String,
    val value: Double,
    val timestamp: Long
)
