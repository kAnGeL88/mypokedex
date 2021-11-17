package com.deloitte.pokedex.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.deloitte.pokedex.database.Converters
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PokemonBase(
    @PrimaryKey
    @NonNull
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String
) : Parcelable

