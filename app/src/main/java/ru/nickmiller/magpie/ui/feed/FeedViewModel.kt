package ru.nickmiller.magpie.ui.feed

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import ru.nickmiller.magpie.data.repository.ArticlesRepository
import ru.nickmiller.magpie.utils.switchMap


class FeedViewModel(app: Application, val repository: ArticlesRepository) : AndroidViewModel(app) {
    private val refresh = MutableLiveData<RefreshAction>()

    val articles
        get() = refresh.switchMap { repository.getArticles() }

    val fetchStatus
        get() = repository.fetchStatus

    fun refresh() = refresh.postValue(RefreshAction.REFRESH)

    enum class RefreshAction {
        REFRESH
    }
}