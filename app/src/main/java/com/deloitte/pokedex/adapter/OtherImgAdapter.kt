package com.deloitte.pokedex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import com.deloitte.pokedex.databinding.ItemImageBinding
import com.deloitte.pokedex.util.getProgressDrawable
import com.deloitte.pokedex.util.loadImage

class OtherImgAdapter(val url: List<String>) :
    RecyclerView.Adapter<OtherImgAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherImgAdapter.ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return url.size
    }


    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val imageUrl = url[position]

            binding.ivPokemon.loadImage(
                imageUrl,
                getProgressDrawable(binding.root.context),
                50
            )

        }
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(position)
    }


}