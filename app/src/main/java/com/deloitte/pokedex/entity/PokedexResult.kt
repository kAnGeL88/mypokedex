package com.deloitte.pokedex.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PokedexResult(
    @Json(name = "count")
    val count: Int = 0,
    @Json(name = "next")
    val next: String,
    @Json(name = "previous")
    val previous: String? = "",
    @Json(name = "results")
    val results: List<PokemonBase>
) : Parcelable