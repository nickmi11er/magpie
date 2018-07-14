package ru.nickmiller.magpie.data.repository

import android.arch.lifecycle.MediatorLiveData
import ru.nickmiller.magpie.data.entity.feedChannel.FeedChannelMapper
import ru.nickmiller.magpie.data.repository.datasource.channels.CloudChannelStore
import ru.nickmiller.magpie.data.repository.datasource.channels.LocalChannelStore
import ru.nickmiller.magpie.model.FeedChannel


class FeedChannelRepository(val fcmapper: FeedChannelMapper, val localStore: LocalChannelStore, val cloudStore: CloudChannelStore) {

    fun getChannels(keyword: String = "", cache: Boolean = false) =
            if (cache) localStore.getChannelsList()
            else {
                val mediatorLiveData = MediatorLiveData<List<FeedChannel>>()
                mediatorLiveData.addSource(localStore.getChannelsList()) { mediatorLiveData.value = it }
                mediatorLiveData.addSource(cloudStore.getChannelsList(keyword)) { mediatorLiveData.value = it }
                mediatorLiveData
            }


    fun subscribe(channel: FeedChannel) {
        channel.isSubscribed = true
        localStore.subscribeChannel(fcmapper.map(channel)!!)
    }

    fun unsubscribe(channel: FeedChannel) {
        channel.isSubscribed = false
        localStore.unsubscribeChannel(fcmapper.map(channel)!!)
    }

}
 
 
