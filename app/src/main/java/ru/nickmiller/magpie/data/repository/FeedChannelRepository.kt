package ru.nickmiller.magpie.data.repository

import android.arch.lifecycle.MutableLiveData
import ru.nickmiller.magpie.data.entity.feedChannel.FeedChannelMapper
import ru.nickmiller.magpie.data.repository.datasource.channels.CloudChannelStore
import ru.nickmiller.magpie.data.repository.datasource.channels.LocalChannelStore
import ru.nickmiller.magpie.model.FeedChannel
import ru.nickmiller.magpie.utils.switchMap
import ru.nickmiller.magpie.utils.zip


class FeedChannelRepository(val fcmapper: FeedChannelMapper, val localStore: LocalChannelStore, val cloudStore: CloudChannelStore) {

    fun getChannels(keyword: String = "", cache: Boolean = false) =
            if (cache) localStore.getChannelsList()
            else {
                cloudStore.getChannelsList(keyword).zip(localStore.getChannelsList()).switchMap {
                    val remote = it.first
                    val local = it.second
                    val tmp = remote.toMutableList()
                    val res = MutableLiveData<List<FeedChannel>>()
                    local.forEach { l ->
                        remote.forEachIndexed { i, r ->
                            if (l.feedId == r.feedId) {
                                tmp[i] = l
                            }
                        }
                    }
                    res.value = tmp
                    res
                }

//                val mediatorLiveData = MediatorLiveData<List<FeedChannel>>()
//                mediatorLiveData.addSource(cloudStore.getChannelsList(keyword)) {
//                    val newElements = it as List<FeedChannel>
//                    val set = mediatorLiveData.value?.toMutableSet()
//                    set?.addAll(newElements)
//                    mediatorLiveData.value = set?.toMutableList()
//                }
//                mediatorLiveData.addSource(localStore.getChannelsList()) { mediatorLiveData.value = it }
//                mediatorLiveData
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
 
 
