package com.deloitte.pokedex.service

import com.deloitte.pokedex.entity.PokedexResult
import com.deloitte.pokedex.entity.PokemonBaseDetails
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokedexServices {

    @GET("pokemon")
    suspend fun getPokemonBaseList (
        @Query("offset") offset : Int? = 10,
        @Query("limit") limit : Int? = 0,
    ) : PokedexResult

    @GET
    suspend fun getPokemonDetails (
        @Url url: String
    ) : PokemonBaseDetails



}