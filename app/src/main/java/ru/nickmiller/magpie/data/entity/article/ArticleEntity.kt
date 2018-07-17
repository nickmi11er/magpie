package ru.nickmiller.magpie.data.entity.article

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date


@Entity
data class ArticleEntity(@PrimaryKey var link: String,
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
                         var channelId: String? = null)
