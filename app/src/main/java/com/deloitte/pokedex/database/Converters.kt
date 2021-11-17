package com.deloitte.pokedex.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.deloitte.pokedex.entity.Sprites
import com.deloitte.pokedex.entity.Stats
import com.deloitte.pokedex.entity.Type
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


@ProvidedTypeConverter
class Converters (private val moshi: Moshi) {

    private val typesClass = Types.newParameterizedType(List::class.java, Type::class.java)
    private val statClass = Types.newParameterizedType(List::class.java, Stats::class.java)

    @TypeConverter
    fun fromSprites(sprites: Sprites?): String? {
        return moshi.adapter(Sprites::class.java).toJson(sprites)
    }


    @TypeConverter
    fun toSprites(sprites: String?): Sprites? {
        return moshi.adapter(Sprites::class.java).fromJson(sprites?:"")
    }

    @TypeConverter
    fun fromStatsList(stats: List<Stats?>): String? {
        return moshi.adapter<List<Stats?>>(statClass).toJson(stats)
    }

    @TypeConverter
    fun toStatsList(stats: String?): List<Stats?> {
        return moshi.adapter<List<Stats?>>(statClass).fromJson(stats?:"").orEmpty()
    }

    @TypeConverter
    fun fromTypeList(types: List<Type?>?): String? {
        return moshi.adapter<List<Type?>>(typesClass).toJson(types)
    }

    @TypeConverter
    fun toTypeList(types: String?): List<Type>? {
        return moshi.adapter<List<Type>>(typesClass).fromJson(types?:"").orEmpty()
    }




}