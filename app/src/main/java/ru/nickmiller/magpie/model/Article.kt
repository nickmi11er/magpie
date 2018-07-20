package ru.nickmiller.magpie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class Article(var link: String? = null,
                   var categories: ArrayList<String> = arrayListOf(),
                   var description: String? = null,
                   var favorite: Boolean = false,
                   var cached: Boolean = false,
                   var cachedTime: Long = 0L,
                   var imageUrl: String? = null,
                   var acTitle: String? = null,
                   var author: String? = null,
                   var acLink: String? = null,
                   var pubDate: Date? = null,
                   var title: String? = null,
                   var acIconUrl: String? = null,
                   var channelId: String? = null,
                   var pureDescription: String? = null,
                   var enclosures: MutableList<Enclosure> = mutableListOf()) : Parcelable {

    override fun equals(other: Any?): Boolean {
        return this.link == (other as Article).link
    }

    override fun hashCode(): Int = link!!.hashCode()
}


@Parcelize
data class Enclosure(var url: String,
                     var type: String? = null,
                     var length: Long = 0L) : Parcelable