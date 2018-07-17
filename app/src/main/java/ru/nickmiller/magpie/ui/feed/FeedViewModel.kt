package ru.nickmiller.magpie.ui.feed

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import ru.nickmiller.magpie.data.repository.ArticlesRepository
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.utils.mainLog
import ru.nickmiller.magpie.utils.switchMap


class FeedViewModel(app: Application, val repository: ArticlesRepository, val bookmarks: Boolean) : AndroidViewModel(app) {
    private val refresh = MutableLiveData<RefreshAction>()
    private val action = MutableLiveData<Action>()

    init {
        mainLog("FeedViewModel was created")
    }

    val articles
        get() = refresh.switchMap { repository.getArticles(bookmarks) }

    val fetchStatus
        get() = repository.fetchStatus

    var clickListener: ((ArticlesAdapter.Action, Article) -> Unit)? = { action, article ->
        when (action) {
            ArticlesAdapter.Action.BOOKMARK -> {
                if (article.favorite) repository.deleteBookmark(article)
                else repository.bookmark(article)
            }
            ArticlesAdapter.Action.SHOW_ARTICLE -> {

            }
        }
    }

    fun refresh() = refresh.postValue(RefreshAction.REFRESH)

    override fun onCleared() {
        mainLog("FeedViewModel on cleared")
        super.onCleared()
    }

    enum class RefreshAction {
        REFRESH
    }

    enum class Action {
        BOOKMARK,
        SHOW_ARTICLE
    }
}