package com.romanuriel.coroutines.model.item


import com.google.gson.annotations.SerializedName

data class Location (
    @SerializedName("name")
    val nameLocation: String,
    @SerializedName("url")
    val urlLocation: String
)
