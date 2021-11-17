package com.deloitte.pokedex.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.deloitte.pokedex.adapter.PokemonLoadStateAdapter
import com.deloitte.pokedex.adapter.PokemonPageAdapter
import com.deloitte.pokedex.databinding.ActivityMainBinding
import com.deloitte.pokedex.dataflow.PokedexDataFlow
import com.deloitte.pokedex.dataflow.PokedexState
import com.deloitte.pokedex.entity.PokemonBaseDetails
import com.deloitte.pokedex.util.POKEMON_DETAILS
import io.uniflow.android.livedata.onStates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalPagingApi
@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pokemonPageAdapter : PokemonPageAdapter
    private lateinit var loadStateAdapter : PokemonLoadStateAdapter
    private val pokedexStateDataFlow: PokedexDataFlow by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        onStates(pokedexStateDataFlow) { state ->
            when (state) {

                is PokedexState.PokemonFetchedState -> {
                    initFromDataFlow(state.flow)
                }

                is PokedexState.ErrorState -> {
                    Toast.makeText(this, state.errorMessage.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecyclerView() {
        pokemonPageAdapter = PokemonPageAdapter{
            val i = Intent(this, DetailsActivity::class.java)
            i.putExtra(POKEMON_DETAILS, it)
            startActivity(i)
        }
        loadStateAdapter = PokemonLoadStateAdapter { pokemonPageAdapter.retry() }
        binding.rvItems.layoutManager = LinearLayoutManager(this)
        binding.rvItems.adapter = pokemonPageAdapter.withLoadStateFooter(loadStateAdapter)
    }

    private fun initFromDataFlow (param : PagingData<PokemonBaseDetails>) {
        lifecycleScope.launch {
            pokemonPageAdapter.submitData(param)
        }
    }
}