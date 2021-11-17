package com.deloitte.pokedex.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.deloitte.pokedex.database.PokedexDatabase
import com.deloitte.pokedex.entity.PokemonBaseDetails
import com.deloitte.pokedex.entity.PokemonRemoteKeys
import com.deloitte.pokedex.service.PokedexRepository
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

@ExperimentalPagingApi
class PokedexRemoteMediator(
    private val pokedexRepository: PokedexRepository,
    private val pokedexDatabase: PokedexDatabase
) : RemoteMediator<Int, PokemonBaseDetails>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonBaseDetails>
    ): MediatorResult {
        try {
            val page = when (loadType) {

                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_INDEX
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    try {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                        remoteKeys?.nextKey ?: return MediatorResult.Success(true)
                    } catch (e: InvalidObjectException) {
                        return MediatorResult.Error(e)
                    }
                }
            }
            val pokemonList = pokedexRepository.getPokemonBaseList(
                limit = state.config.pageSize,
                offset = page * state.config.pageSize
            )

            val pokemonDetailedList = pokemonList.map { pokemonListElement ->
                pokedexRepository.getPokemonDetails(pokemonListElement.url)
            }

            val endOfPaginationReached = pokemonDetailedList.size < state.config.pageSize

            pokedexDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokedexDatabase.pokedexDao().deleteAll()
                    pokedexDatabase.remoteKeysDao().clearRemoteKeys()
                }

                val prevKey = if (page == STARTING_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = pokemonList.map {
                    PokemonRemoteKeys(
                        name = it.name,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                pokedexDatabase.pokedexDao().addAllPokemon(pokemonDetailedList)
                pokedexDatabase.remoteKeysDao().insertAll(keys)


            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }


    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonBaseDetails>): PokemonRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            .let { pokemon ->
                pokedexDatabase.withTransaction {
                    pokedexDatabase.remoteKeysDao().remoteKeyByPokemonName(pokemon?.name)
                }
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonBaseDetails>): PokemonRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { name ->
                pokedexDatabase.withTransaction {
                    pokedexDatabase.remoteKeysDao().remoteKeyByPokemonName(name)
                }
            }
        }

    }

    override suspend fun initialize(): InitializeAction {
        val a = super.initialize()
        return a
    }



    companion object {
        const val STARTING_INDEX = 0
    }
}