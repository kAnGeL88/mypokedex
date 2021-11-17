package com.deloitte.pokedex.dataflow


import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.deloitte.pokedex.database.PokedexDatabase
import com.deloitte.pokedex.entity.PokemonBaseDetails
import com.deloitte.pokedex.paging.PokedexRemoteMediator
import com.deloitte.pokedex.service.PokedexRepository
import io.uniflow.android.AndroidDataFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@ExperimentalPagingApi
class PokedexDataFlow(
    private val pokedexRepository: PokedexRepository,
    private val pokedexDatabase: PokedexDatabase,
) : AndroidDataFlow() {

    init {
        viewModelScope.launch {
            getPokemonsFlow()
        }
    }

    private fun getPokemonsFlow() = action {
        try {
            getPokemonList().collectLatest {
                setState(PokedexState.PokemonFetchedState (it))
            }
        } catch (e: Exception) {
            setState { PokedexState.ErrorState(e) }
        }
    }


    private fun getPokemonList(): Flow<PagingData<PokemonBaseDetails>> {

        val prevSource = { pokedexDatabase.pokedexDao().getAllPokemon() }

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                prefetchDistance = 4
            ),
            remoteMediator = PokedexRemoteMediator(
                pokedexRepository,
                pokedexDatabase
            ),
            pagingSourceFactory = prevSource

        ).flow.cachedIn(viewModelScope)
    }


}