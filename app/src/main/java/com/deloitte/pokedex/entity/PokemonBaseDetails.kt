package com.deloitte.pokedex.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "PokemonBaseDetails")
@JsonClass(generateAdapter = true)
data class PokemonBaseDetails(
    @Json(name ="base_experience")
    val baseExperience: Int,
    @Json(name ="height")
    val height: Int,
    @PrimaryKey(autoGenerate = false)
    @Json(name ="id")
    val id: Int,
    @Json(name ="name")
    val name: String,
    @Json(name ="order")
    val order: Int,
    @Json(name ="sprites")
    val sprites: Sprites? = null,
    @Json(name ="stats")
    val stats: List<Stats> = listOf(),
    @Json(name ="types")
    val types: List<Type> = listOf(),
    @Json(name ="weight")
    val weight: Int
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Sprites(
    @Json(name ="front_default")
    val frontDefault: String? = null,
    @Json(name ="back_default")
    val backDefault: String? = null,
    @Json(name ="back_female")
    val backFemale: String? = null,
    @Json(name ="back_shiny_female")
    val backShinyFemale: String? = null,
    @Json(name ="back_shiny")
    val backShiny: String? = null,
    @Json(name ="front_female")
    val frontFemale: String? = null,
    @Json(name ="front_shiny")
    val frontShiny: String? = null,
    @Json(name ="front_shiny_female")
    val frontShinyFemale: String? = null,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Stats(
    @Json(name ="base_stat")
    val baseStat: Int,
    @Json(name ="effort")
    val effort: Int,
    @Json(name ="stat")
    val stat: Details
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Details(
    @Json(name ="name")
    val name: String,
    @Json(name ="url")
    val url: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Type(
    @Json(name ="slot")
    val slot: Int,
    @Json(name ="type")
    val type: TypeDetails
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class TypeDetails(
    @Json(name ="name")
    val name: String,
    @Json(name ="url")
    val url: String
) : Parcelable