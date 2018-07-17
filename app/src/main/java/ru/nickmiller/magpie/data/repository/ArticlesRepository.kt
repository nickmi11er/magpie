package ru.nickmiller.magpie.data.repository

import android.arch.lifecycle.MediatorLiveData
import ru.nickmiller.magpie.data.repository.datasource.articles.CloudArticlesStore
import ru.nickmiller.magpie.data.repository.datasource.articles.LocalArticlesStore
import ru.nickmiller.magpie.model.Article


class ArticlesRepository(val cloudStore: CloudArticlesStore, val localStore: LocalArticlesStore) {
    val fetchStatus
        get() = cloudStore.fetchStatus

    fun getArticles(bookmarks: Boolean = false) =
            if (bookmarks) localStore.getBookmarks()
            else MediatorLiveData<List<Article>>().apply {
                addSource(localStore.getArticles()) { postValue(it) }

                addSource(cloudStore.getArticles()) {
                    it?.let {
                        localStore.cacheArticles(it).subscribe()
                    }
                }
            }

    fun bookmark(article: Article) = localStore.bookmark(article).subscribe()

    fun deleteBookmark(article: Article) = localStore.deleteBookmark(article).subscribe()
}