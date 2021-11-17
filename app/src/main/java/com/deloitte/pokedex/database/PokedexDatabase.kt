package com.deloitte.pokedex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deloitte.pokedex.entity.PokemonBaseDetails
import com.deloitte.pokedex.entity.PokemonRemoteKeys


    private const val POKEDEX_DB = "pokedex_database"

    @Database(
        entities = [
            PokemonBaseDetails::class,
            PokemonRemoteKeys::class
        ],
        version = 1,
        exportSchema = false
    )

    @TypeConverters(Converters::class)
    abstract class PokedexDatabase : RoomDatabase() {
    companion object {
        fun create(
            context: Context,
            converters: Converters
        ): PokedexDatabase {
            val database = Room.databaseBuilder(context, PokedexDatabase::class.java, POKEDEX_DB)
            return database
                .addTypeConverter(converters)
                .fallbackToDestructiveMigration()
                .build()
        }


    }
        abstract fun pokedexDao(): PokedexDao
        abstract fun remoteKeysDao(): RemoteKeysDao
    }
