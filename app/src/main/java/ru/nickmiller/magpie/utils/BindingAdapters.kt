package ru.nickmiller.magpie.utils

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

val sdf = SimpleDateFormat("dd MM yyyy", Locale.getDefault())

@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String?) {
    if (url != null) {
        view.visibility = View.VISIBLE
        Glide.with(view.context).load(url).into(view)
    }
}


@BindingAdapter("app:articleDate")
fun articleDate(view: TextView, date: Date?) {
    date?.let {
        val minutes: Long = (Date().time - date.time) / (1000 * 60)
        view.text = when {
            minutes < 0 -> "recent"
            minutes < 60 -> "$minutes minutes ago"
            minutes / 60 < 24 -> "${minutes / 60} hours ago"
            minutes / (60 * 24) < 30 -> "${minutes / (60 * 24)} days ago"
            else -> sdf.format(it)
        }
    }
}