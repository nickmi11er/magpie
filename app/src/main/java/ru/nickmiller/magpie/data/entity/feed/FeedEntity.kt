package ru.nickmiller.magpie.data.entity.feed

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ru.nickmiller.magpie.data.entity.article.ArticleEntity


@Entity
data class FeedEntity(@PrimaryKey var link: String? = null,
                      var articles: MutableList<ArticleEntity> = mutableListOf(),
                      var description: String? = null,
                      var copyright: String? = null,
                      var generator: String? = null,
                      var encoding: String? = null,
                      var language: String? = null,
                      var imgUrl: String? = null,
                      var author: String? = null,
                      var title: String? = null,
                      var acIconUrl: String? = null)