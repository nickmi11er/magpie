package ru.nickmiller.magpie.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String?) {
    if (url != null) Glide.with(view.context).load(url).into(view)
}
 
 
