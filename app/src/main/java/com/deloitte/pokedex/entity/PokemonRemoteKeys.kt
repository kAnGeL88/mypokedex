package com.deloitte.pokedex.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RemoteKeys")
data class PokemonRemoteKeys (
    @PrimaryKey
    @NonNull
    val name: String,
    val prevKey: Int?,
    val nextKey: Int?
)