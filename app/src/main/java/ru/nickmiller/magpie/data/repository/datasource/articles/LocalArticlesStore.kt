package ru.nickmiller.magpie.data.repository.datasource.articles

import android.arch.lifecycle.LiveData
import ru.nickmiller.magpie.data.cache.dao.ArticlesDao
import ru.nickmiller.magpie.data.entity.article.ArticleMapper
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.utils.Resource
import ru.nickmiller.magpie.utils.map


class LocalArticlesStore(val dao: ArticlesDao, val mapper: ArticleMapper) {

    fun getArticles(): LiveData<List<Article>> =
            dao.getAllArticles().map { mapper.transform(it) }

}