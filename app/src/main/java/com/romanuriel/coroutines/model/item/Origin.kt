package com.romanuriel.coroutines.model.item

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Origin (
    @SerializedName("name")
    val nameOrigin: String,
    @SerializedName("url")
    val urlOrigin: String
): Parcelable
