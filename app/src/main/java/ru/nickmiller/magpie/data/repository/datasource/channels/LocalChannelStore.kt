package ru.nickmiller.magpie.data.repository.datasource.channels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.magpie.data.cache.dao.ArticlesDao
import ru.nickmiller.magpie.data.cache.dao.ChannelsDao
import ru.nickmiller.magpie.data.entity.feedChannel.FeedChannelEntity
import ru.nickmiller.magpie.data.entity.feedChannel.FeedChannelMapper
import ru.nickmiller.magpie.model.FeedChannel


class LocalChannelStore(val dao: ChannelsDao, val artsDao: ArticlesDao, val mapper: FeedChannelMapper) {

    fun getChannelsList(): LiveData<List<FeedChannel>> =
            Transformations.map(dao.getAllSubs()) { mapper.transform(it) }

    fun subscribeChannel(channelEntity: FeedChannelEntity) = Single.fromCallable {
        dao.insertSub(channelEntity)
    }
            .subscribeOn(Schedulers.io())
            .subscribe()


    fun unsubscribeChannel(channelEntity: FeedChannelEntity) = Single.fromCallable {
        dao.deleteSub(channelEntity)
        artsDao.clearCacheWithChannel(channelEntity.feedId)
    }
            .subscribeOn(Schedulers.io())
            .subscribe()

}