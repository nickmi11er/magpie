package ru.nickmiller.magpie.ui.channels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import android.view.View
import ru.nickmiller.magpie.data.repository.FeedChannelRepository
import ru.nickmiller.magpie.model.FeedChannel
import ru.nickmiller.magpie.ui.search.SearchActivity


class ChannelsViewModel(val app: Application, val repository: FeedChannelRepository) : AndroidViewModel(app) {
    val channels
        get() = repository.getChannels(cache = true)

    fun onSearchChannel(view: View) = app.startActivity(Intent(app, SearchActivity::class.java))

    fun onChannelClick(action: FeedChannelAdapter.ChActionType, channel: FeedChannel) {
        when (action) {
            FeedChannelAdapter.ChActionType.SUBSCRIBE -> repository.subscribe(channel)
            FeedChannelAdapter.ChActionType.UNSUBSCRIBE -> repository.unsubscribe(channel)
            FeedChannelAdapter.ChActionType.OPEN_URL -> println(channel.feedId)
        }
    }
}