package com.deloitte.pokedex.service

import com.deloitte.pokedex.entity.PokemonBase
import com.deloitte.pokedex.entity.PokemonBaseDetails
import javax.inject.Inject

class PokedexRepositoryImpl @Inject constructor(
    private val pokedexServices: PokedexServices,
) : PokedexRepository {

    override suspend fun getPokemonDetails(url: String): PokemonBaseDetails {
        return pokedexServices.getPokemonDetails(url)
    }

    override suspend fun getPokemonBaseList(offset: Int, limit: Int): List<PokemonBase> {
        return pokedexServices.getPokemonBaseList(offset, limit).results
    }


}