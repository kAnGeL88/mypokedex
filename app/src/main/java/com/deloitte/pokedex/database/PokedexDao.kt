package com.deloitte.pokedex.database

import androidx.paging.PagingSource
import androidx.room.*
import com.deloitte.pokedex.entity.PokedexResult
import com.deloitte.pokedex.entity.PokemonBase
import com.deloitte.pokedex.entity.PokemonBaseDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface PokedexDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllPokemon (list: List<PokemonBaseDetails>)

    @Query("SELECT * FROM PokemonBaseDetails")
    fun getAllPokemon(): PagingSource<Int, PokemonBaseDetails>

    @Query("DELETE from PokemonBaseDetails")
    suspend fun deleteAll()

}