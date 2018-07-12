package ru.nickmiller.magpie.model


data class Feed(var articles: MutableList<Article> = mutableListOf(),
                var description: String? = null,
                var copyright: String? = null,
                var generator: String? = null,
                var encoding: String? = null,
                var language: String? = null,
                var imgUrl: String? = null,
                var author: String? = null,
                var title: String? = null,
                var link: String? = null,
                var acIconUrl: String? = null)