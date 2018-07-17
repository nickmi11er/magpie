package ru.nickmiller.magpie.ui.channels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import android.view.View
import ru.nickmiller.magpie.data.repository.FeedChannelRepository
import ru.nickmiller.magpie.model.FeedChannel
import ru.nickmiller.magpie.model.Topic


class ChannelsViewModel(val app: Application, val repository: FeedChannelRepository) : AndroidViewModel(app) {
    val channels
        get() = repository.getChannels(cache = true)

    fun onSearchChannel(view: View) = app.startActivity(Intent(app, SearchActivity::class.java))

    fun onChannelClick(action: FeedChannelAdapter.ChActionType, channel: FeedChannel?, topic: Topic?) {
        when (action) {
            FeedChannelAdapter.ChActionType.SUBSCRIBE -> channel?.let { repository.subscribe(it) }
            FeedChannelAdapter.ChActionType.UNSUBSCRIBE -> channel?.let { repository.unsubscribe(it) }
            FeedChannelAdapter.ChActionType.OPEN_URL -> println(channel?.feedId)
            FeedChannelAdapter.ChActionType.OPEN_TOPIC -> {  }
        }
    }
}