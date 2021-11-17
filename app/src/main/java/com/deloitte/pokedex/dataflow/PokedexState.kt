package com.deloitte.pokedex.dataflow

import androidx.paging.PagingData
import com.deloitte.pokedex.entity.PokemonBase
import com.deloitte.pokedex.entity.PokemonBaseDetails
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

sealed class PokedexState : UIState() {

    object Loading : PokedexState()
    data class ErrorState (val errorMessage : Exception) : PokedexState()
    data class PokemonFetchedState(val flow: PagingData<PokemonBaseDetails>) : PokedexState()

}