package com.example.simplecachingexample.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history")
data class HistoryModel(
    @PrimaryKey(autoGenerate = false) val firstKey: String,
    val firstValue: Double,
    val secondKey: String,
    val secondValue: Double,
    val date: Long
)
