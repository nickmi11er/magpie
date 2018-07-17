package ru.nickmiller.magpie.ui.channels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.view.inputmethod.EditorInfo
import ru.nickmiller.magpie.data.repository.FeedChannelRepository
import ru.nickmiller.magpie.model.FeedChannel
import ru.nickmiller.magpie.model.Topic


class SearchViewModel(val app: Application, val repository: FeedChannelRepository) : AndroidViewModel(app) {
    val searchText = MutableLiveData<String>()
    val searchAction = MutableLiveData<Pair<SearchAction, String>>()
    var channels = Transformations.switchMap(searchAction) {
        if (it.first == SearchAction.SEARCH_CLICK) searchChannels(it.second)
        else topicChannels(it.second)
    }

    fun onSearchTextChanged(text: CharSequence, count: Int) {
        searchText.value = text.toString()
    }

    private fun searchChannels(keyword: String) = repository.getChannels(keyword)

    private fun topicChannels(title: String) = repository.getTopicChannel(title)

    fun onSearchAction(text: String, actionId: Int?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchAction.value = Pair(SearchAction.SEARCH_CLICK, text)
            return true
        }
        return false
    }

    fun onChannelClick(action: FeedChannelAdapter.ChActionType, channel: FeedChannel?, topic: Topic?) {
        when (action) {
            FeedChannelAdapter.ChActionType.SUBSCRIBE -> channel?.let { repository.subscribe(it) }
            FeedChannelAdapter.ChActionType.UNSUBSCRIBE -> channel?.let { repository.unsubscribe(it) }
            FeedChannelAdapter.ChActionType.OPEN_URL -> println(channel?.feedId)
            FeedChannelAdapter.ChActionType.OPEN_TOPIC -> topic?.let { searchAction.postValue(Pair(SearchAction.TOPIC_CLICK, it.title)) }
        }
    }

    enum class SearchAction {
        SEARCH_CLICK,
        TOPIC_CLICK
    }
}