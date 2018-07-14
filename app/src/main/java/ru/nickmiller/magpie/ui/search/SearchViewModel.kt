package ru.nickmiller.magpie.ui.search

import android.app.Application
import android.arch.lifecycle.*
import android.view.inputmethod.EditorInfo
import ru.nickmiller.magpie.data.repository.FeedChannelRepository


class SearchViewModel(val app: Application, val repository: FeedChannelRepository) : AndroidViewModel(app) {
    val searchText = MutableLiveData<String>()
    val searchAction = MutableLiveData<String>()
    var channels = Transformations.switchMap(searchAction) { searchChannels(it) }

    fun onSearchTextChanged(text: CharSequence, count: Int) {
        searchText.value = text.toString()
    }

    private fun searchChannels(keyword: String) = repository.getChannels(keyword)

    fun onSearchAction(text: String?, actionId: Int?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchAction.value = text
            return true
        }
        return false
    }
}