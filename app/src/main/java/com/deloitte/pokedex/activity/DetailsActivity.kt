package com.deloitte.pokedex.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deloitte.pokedex.R
import com.deloitte.pokedex.adapter.OtherImgAdapter
import com.deloitte.pokedex.databinding.ActivityPokemonDetailsBinding
import com.deloitte.pokedex.domain.PokemonSpite
import com.deloitte.pokedex.entity.PokemonBaseDetails
import com.deloitte.pokedex.util.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPokemonDetailsBinding
    private lateinit var otherImgAdapter : OtherImgAdapter
    private var pokemonDetails: PokemonBaseDetails? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.extras?.let { bundle ->
            pokemonDetails = bundle.get(POKEMON_DETAILS) as PokemonBaseDetails

            binding.ivArrow.setOnClickListener {
                super.onBackPressed()
            }

            val pokemonSpriteList = mutableListOf<PokemonSpite>()

            pokemonDetails?.sprites?.frontDefault?.let { pokemonSpriteList.add(PokemonSpite(FRONT_DEFAULT,it)) }
            pokemonDetails?.sprites?.backDefault?.let  { pokemonSpriteList.add(PokemonSpite(BACK_DEFAULT,it)) }
            pokemonDetails?.sprites?.frontFemale?.let  { pokemonSpriteList.add(PokemonSpite(FRONT_FEMALE,it)) }
            pokemonDetails?.sprites?.backFemale?.let { pokemonSpriteList.add(PokemonSpite(BACK_FEMALE,it)) }
            pokemonDetails?.sprites?.frontShiny?.let { pokemonSpriteList.add(PokemonSpite(FRONT_SHINY,it)) }
            pokemonDetails?.sprites?.backShiny?.let { pokemonSpriteList.add(PokemonSpite(BACK_SHINY,it)) }
            pokemonDetails?.sprites?.frontShinyFemale?.let  { pokemonSpriteList.add(PokemonSpite(FRONT_FEMALE,it)) }
            pokemonDetails?.sprites?.backShinyFemale?.let { pokemonSpriteList.add(PokemonSpite(BACK_SHINY_FEMALE,it)) }

            initRecyclerView (pokemonSpriteList)

            val picture = getPicById(pokemonDetails?.id?:0)

            binding.ivPokemon.loadImage(
                picture,
                getProgressDrawable(binding.root.context),
                50,
                this.window,
                binding.llToolbar
            )

            binding.tvTypes.text = "${getString(R.string.types)}: ${pokemonDetails?.types?.joinToString(",") { it.type.name }}"

            binding.tvPokemonName.text =  pokemonDetails?.name

            binding.tvWeight.text = "${pokemonDetails?.weight?.toHumanFormat()} ${getString(R.string.kg)}"
            binding.tvHeight.text = "${pokemonDetails?.height?.toHumanFormat()} ${getString(R.string.m)}"

            binding.ratings.progressHp.animateStats(pokemonDetails?.stats?.first { it.stat.name == HP }?.baseStat?:0)
            binding.ratings.progressSpeed.animateStats(pokemonDetails?.stats?.first { it.stat.name == SPEED }?.baseStat?:0)
            binding.ratings.progressAttack.animateStats(pokemonDetails?.stats?.first { it.stat.name == ATTACK }?.baseStat?:0)
            binding.ratings.progressDefense.animateStats(pokemonDetails?.stats?.first { it.stat.name == DEFENSE }?.baseStat?:0)
            binding.ratings.progressSuperAttack.animateStats(pokemonDetails?.stats?.first { it.stat.name == SPECIAL_ATTACK }?.baseStat?:0)
            binding.ratings.progressSuperDefense.animateStats(pokemonDetails?.stats?.first { it.stat.name == SPECIAL_DEFENCE }?.baseStat?:0)

            binding.ratings.tvRatingsHp.text= (pokemonDetails?.stats?.first { it.stat.name == HP }?.baseStat?:0).toString()
            binding.ratings.tvRatingsSpeed.text= (pokemonDetails?.stats?.first { it.stat.name == SPEED }?.baseStat?:0).toString()
            binding.ratings.tvRatingsAttack.text= (pokemonDetails?.stats?.first { it.stat.name == ATTACK }?.baseStat?:0).toString()
            binding.ratings.tvRatingsDefense.text= (pokemonDetails?.stats?.first { it.stat.name == DEFENSE }?.baseStat?:0).toString()
            binding.ratings.tvRatingsSuperAttack.text= (pokemonDetails?.stats?.first { it.stat.name == SPECIAL_ATTACK }?.baseStat?:0).toString()
            binding.ratings.tvRatingsSuperDefense.text=  (pokemonDetails?.stats?.first { it.stat.name == SPECIAL_DEFENCE }?.baseStat?:0).toString()
        }
    }

    private fun initRecyclerView(imageUrlList: MutableList<PokemonSpite>) {

        otherImgAdapter = OtherImgAdapter(imageUrlList)
        binding.rvImages.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvImages.adapter = otherImgAdapter

    }

}
