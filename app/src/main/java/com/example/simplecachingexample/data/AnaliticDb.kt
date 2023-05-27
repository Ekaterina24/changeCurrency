package com.example.simplecachingexample.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "analyticDb")
data class AnalyticDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val key: String,
//    val array: List<String>
    val value: Double,
    val timestamp: Long
)

//data class Values(val value: String)


//class ValuesTypeConverter {
//
//    @TypeConverter
//    fun listToJson(value: List<String>?) = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
//}
