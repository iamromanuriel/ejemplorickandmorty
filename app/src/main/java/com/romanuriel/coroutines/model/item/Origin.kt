package com.romanuriel.coroutines.model.item

import com.google.gson.annotations.SerializedName

data class Origin (
    @SerializedName("name")
    val nameOrigin: String,
    @SerializedName("url")
    val urlOrigin: String
)
