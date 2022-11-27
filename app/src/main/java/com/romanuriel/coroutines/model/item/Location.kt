package com.romanuriel.coroutines.model.item


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location (
    @SerializedName("name")
    val nameLocation: String,
    @SerializedName("url")
    val urlLocation: String
): Parcelable
