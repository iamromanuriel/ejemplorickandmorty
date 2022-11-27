package com.romanuriel.coroutines.model.item

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tb_characters")
data class CharacterItem (
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val type: String,
    val gender: String,
    @Embedded
    val origin: Origin,
    @Embedded
    val location: Location,
    val image: String,
    //val episode: List<String>,
    val created: String
        ): Parcelable