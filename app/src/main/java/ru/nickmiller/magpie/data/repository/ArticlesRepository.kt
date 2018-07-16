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
                addSource(localStore.getArticles()) {
                    //val tmp = if (this.value != null) this.value as MutableList else mutableListOf()
                    //tmp.addAll(it as MutableList)
                    //this.value = tmp
                    postValue(it)
                }

                addSource(cloudStore.getArticles()) {
                    //val tmp = if (this.value != null) this.value as MutableList else mutableListOf()
                    //tmp.addAll(it as MutableList)
                    //this.value = tmp
                    postValue(it)
                }
            }

}