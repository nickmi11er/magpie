package ru.nickmiller.magpie.utils

import android.databinding.BindingAdapter
import android.graphics.drawable.GradientDrawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.nickmiller.magpie.R
import java.text.SimpleDateFormat
import java.util.*

val sdf = SimpleDateFormat("dd MM yyyy", Locale.getDefault())

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("app:url")
    fun loadImage(view: ImageView, url: String?) {
        if (url != null) {
            view.visibility = View.VISIBLE
            Glide.with(view.context).load(url).into(view)
        } else {
            view.visibility = View.GONE
        }
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

@BindingAdapter("app:bookmark")
fun bookmarkState(view: ImageButton, isBookmarked: Boolean) {
    val drawableId =
            if (isBookmarked) R.drawable.ic_bookmark_fill
            else R.drawable.ic_bookmark
    view.setImageDrawable(ContextCompat.getDrawable(view.context, drawableId))
}

@BindingAdapter("app:topicCardBackground")
fun topicBackgroundColor(view: CardView, @ColorRes colorId: Int) {
    val drawable = ContextCompat.getDrawable(view.context, R.drawable.bg_topic) as GradientDrawable
    val color = ContextCompat.getColor(view.context, colorId)
    drawable.setColor(color)
    //view.setBackgroundResource(drawable)
    view.background = drawable
}

@BindingAdapter("app:topicIcon")
fun topicIcon(view: ImageView, @DrawableRes drawableId: Int) {
    view.setImageDrawable(ContextCompat.getDrawable(view.context, drawableId))
}