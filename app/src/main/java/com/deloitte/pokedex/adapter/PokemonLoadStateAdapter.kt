package com.deloitte.pokedex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deloitte.pokedex.databinding.LayoutLoadingBinding

class PokemonLoadStateAdapter(
    private val reload: () -> Unit
) :
    LoadStateAdapter<PokemonLoadStateAdapter.LoadingStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder =

        LoadingStateViewHolder(
            LayoutLoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    inner class LoadingStateViewHolder(private val binding: LayoutLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.progress.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isInvisible = loadState !is LoadState.Error

            binding.btnRetry.setOnClickListener {
                reload()
            }
        }
    }
}

