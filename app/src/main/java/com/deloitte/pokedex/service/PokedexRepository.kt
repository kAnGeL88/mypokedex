package com.deloitte.pokedex.service

import com.deloitte.pokedex.entity.PokemonBase
import com.deloitte.pokedex.entity.PokemonBaseDetails

interface PokedexRepository {

    suspend fun getPokemonDetails(
        url: String,
    ): PokemonBaseDetails

    suspend fun getPokemonBaseList(
        offset: Int,
        limit: Int,
    ): List<PokemonBase>


}