package ru.nickmiller.magpie.data.repository

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import ru.nickmiller.magpie.data.repository.datasource.articles.CloudArticlesStore
import ru.nickmiller.magpie.data.repository.datasource.articles.LocalArticlesStore
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.utils.FetchProgress
import ru.nickmiller.magpie.utils.switchMap


class ArticlesRepository(val cloudStore: CloudArticlesStore, val localStore: LocalArticlesStore) {
    val fetchStatus
        get() = cloudStore.fetchStatus

    fun getArticles(bookmarks: Boolean = false, forceRefresh: Boolean = false) =
            if (bookmarks) localStore.getBookmarks()
            else MediatorLiveData<List<Article>>().apply {
                addSource(localStore.getArticles()) { postValue(it) }

                addSource(cloudStore.dao.getAllSubs().switchMap { channels ->
                    if (forceRefresh) cloudStore.getArticles(channels)
                    else {
                        var checksum = 0
                        channels.forEach { checksum += it.hashCode() }
                        if (FetchProgress.canFetch(checksum)) cloudStore.getArticles(channels)
                        else MutableLiveData()
                    }
                }) {
                    it?.let {
                        localStore.cacheArticles(it).subscribe()
                    }
                }

//                addSource(cloudStore.getArticles()) {
//                    it?.let {
//                        localStore.cacheArticles(it).subscribe()
//                    }
//                }
            }

    fun bookmark(article: Article) = localStore.bookmark(article).subscribe()

    fun deleteBookmark(article: Article) = localStore.deleteBookmark(article).subscribe()
}