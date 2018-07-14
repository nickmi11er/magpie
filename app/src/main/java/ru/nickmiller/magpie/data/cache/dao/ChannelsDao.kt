package ru.nickmiller.magpie.data.cache.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import ru.nickmiller.magpie.data.entity.feedChannel.FeedChannelEntity


@Dao
interface ChannelsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(subs: List<FeedChannelEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSub(sub: FeedChannelEntity)

    @Delete
    fun deleteSub(sub: FeedChannelEntity)

    @Query("SELECT * FROM feedchannelentity")
    fun getAllSubs(): LiveData<List<FeedChannelEntity>>
}