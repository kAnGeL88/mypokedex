package com.deloitte.pokedex.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.deloitte.pokedex.R
import com.deloitte.pokedex.databinding.ItemPokemonBinding
import com.deloitte.pokedex.entity.PokemonBaseDetails
import com.deloitte.pokedex.util.getPicById
import com.deloitte.pokedex.util.getProgressDrawable
import com.deloitte.pokedex.util.loadImage
import java.util.*

@SuppressLint("SetTextI18n")
@RequiresApi(Build.VERSION_CODES.O)
class PokemonPageAdapter (
    private val onPokemonClicked: (name : PokemonBaseDetails) -> Unit
        ) :
    PagingDataAdapter<PokemonBaseDetails, PokemonPageAdapter.PokemonViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder =
        PokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonBaseDetails) {
            binding.tvPokemonName.text = item.name.capitalize(Locale.ROOT)

            val picture = getPicById(item.id)

            if (picture.isEmpty()) {
                binding.ivPokemon.loadImage(
                    ContextCompat.getDrawable(binding.root.context, R.drawable.not_found),
                    getProgressDrawable(binding.root.context),
                    50
                )
            } else {
                binding.ivPokemon.loadImage(
                    picture,
                    getProgressDrawable(binding.root.context),
                    50
                )
            }

            binding.root.setOnClickListener {
                onPokemonClicked(item)
            }

        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<PokemonBaseDetails>() {
        override fun areItemsTheSame(oldItem: PokemonBaseDetails, newItem: PokemonBaseDetails): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PokemonBaseDetails, newItem: PokemonBaseDetails): Boolean {
            return oldItem.name == newItem.name
        }

    }



}