package com.mysticraccoon.travelandeat.core.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mysticraccoon.travelandeat.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imageUrl: String?) {
    imageUrl?.let { url ->
        Glide.with(imgView)
            .load(url)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_no_food)
            .into(imgView)
    }
}