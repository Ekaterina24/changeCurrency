package com.example.simplecachingexample.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlin.math.roundToInt

@Entity(tableName = "currenciesDb")
@Parcelize
data class CurrencyDb(
    @PrimaryKey(autoGenerate = false) val key: String,
    val value: Double,
    val timestamp: Long,
    var isFavorite: Boolean
) : Parcelable

fun CurrencyDb.toFavoriteCurrency() = FavoriteCurrencyDb(
    key = key,
    value = value,
    timestamp = timestamp
)

fun CurrencyDb.toAnalyticCurrency() = AnalyticDb(
    id = 0,
    key = key,
    value = ((value * 100.0).roundToInt() / 100.0),
//    value = ((value * 100.0).roundToInt() / 100.0),
    timestamp = timestamp
)