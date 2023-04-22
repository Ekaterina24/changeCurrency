package com.example.simplecachingexample.data

import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("base") val base: String,
    @SerializedName("name") val name: String,
    @SerializedName("rates") val rates: Map<String, Double>,
    @SerializedName("success") val success: Boolean,
    @SerializedName("timestamp") val timestamp: String,
)

