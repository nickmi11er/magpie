package ru.nickmiller.magpie.data.repository.datasource.articles

import android.arch.lifecycle.LiveData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.magpie.data.cache.dao.ArticlesDao
import ru.nickmiller.magpie.data.entity.article.ArticleMapper
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.utils.map


class LocalArticlesStore(val dao: ArticlesDao, val mapper: ArticleMapper) {

    fun getArticles(): LiveData<List<Article>> =
            dao.getAllArticles().map { mapper.transform(it) }

    fun getBookmarks(): LiveData<List<Article>> =
            dao.getFavoriteArticles().map { mapper.transform(it) }

    fun cacheArticles(it: List<Article>) = it.let { articles ->
        Single.fromCallable {
            dao.cacheArticles(mapper.map(articles))
        }
                .subscribeOn(Schedulers.newThread())
    }

    fun bookmark(article: Article) =
            Single.fromCallable {
                article.favorite = true
                dao.insertArticle(mapper.map(article))
            }
                    .subscribeOn(Schedulers.newThread())

    fun deleteBookmark(article: Article) =
            Single.fromCallable {
                article.favorite = false
                dao.insertArticle(mapper.map(article))
            }
                    .subscribeOn(Schedulers.newThread())

}