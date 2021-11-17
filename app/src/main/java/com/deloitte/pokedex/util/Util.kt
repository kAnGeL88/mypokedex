package com.deloitte.pokedex.util

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.deloitte.pokedex.R
import java.util.*

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 20f
        start()
    }
}

fun String.extractId() = this.substringAfter("pokemon").replace("/", "").toInt()

fun String.getPicUrl(): String {
    val id = this.extractId()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
}

fun getPicById(id : Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable, cornerRadius: Int? = null) {
    val corner = RequestOptions()
    cornerRadius?.let {
        corner.transform(
            RoundedCorners(it)
        )
    }

    val context = this.context
    val imageView = this

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.not_found)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .apply(corner)
        .listener(object : RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                val drawable = resource as BitmapDrawable
                val bitmap = drawable.bitmap
                Palette.Builder(bitmap).generate {
                    it?.let { palette ->


                        val mainColor = palette.getLightVibrantColor(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        )
                        imageView.setBackgroundColor(mainColor)
                    }
                }
                return false
            }
        })
        .into(imageView)
}

fun ImageView.loadImage(uri: String?,
                        progressDrawable: CircularProgressDrawable,
                        cornerRadius: Int? = null,
                        window: Window,
                        toolbar: ConstraintLayout) {

    val corner = RequestOptions()
    cornerRadius?.let {
        corner.transform(
            RoundedCorners(it)
        )
    }

    val context = this.context
    val imageView = this

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.not_found)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .apply(corner)
        .listener(object : RequestListener<Drawable>{
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                val drawable = resource as BitmapDrawable
                val bitmap = drawable.bitmap
                Palette.Builder(bitmap).generate {
                    it?.let { palette ->

                        val mainColor = palette.getLightVibrantColor(
                            ContextCompat.getColor(
                                context,
                                R.color.white
                            )
                        )
                        window.statusBarColor = mainColor
                        imageView.setBackgroundColor(mainColor)
                        toolbar.setBackgroundColor(mainColor)
                    }
                }
                return false
            }
        })
        .into(imageView)

}

fun ImageView.loadImage(drawable: Drawable?, progressDrawable: CircularProgressDrawable, cornerRadius: Int? = null ) {
    val corner = RequestOptions()
    cornerRadius?.let {
        corner.transform(
            RoundedCorners(it)
        )
    }

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.not_found)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(drawable)
        .apply(corner)
        .into(this)
}

fun Int.toHumanFormat() = "%.${1}f".format((this.toDouble().div(10)), Locale.ROOT)

fun ProgressBar.animateStats (value : Int) {
    this.max = 200
    ObjectAnimator.ofInt(this, "progress", value)
        .setDuration(2000)
        .start()
}

