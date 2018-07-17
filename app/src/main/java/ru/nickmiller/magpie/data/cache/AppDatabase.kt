package ru.nickmiller.magpie.data.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ru.nickmiller.magpie.data.cache.dao.ArticlesDao
import ru.nickmiller.magpie.data.cache.dao.ChannelsDao
import ru.nickmiller.magpie.data.cache.dao.Converters
import ru.nickmiller.magpie.data.entity.article.ArticleEntity
import ru.nickmiller.magpie.data.entity.feed.FeedEntity
import ru.nickmiller.magpie.data.entity.feedChannel.FeedChannelEntity


@Database(entities = [(FeedChannelEntity::class), (ArticleEntity::class)], version = 3)
@TypeConverters(value = [(Converters::class)])
abstract class AppDatabase : RoomDatabase() {
    abstract fun getChannelDao(): ChannelsDao
    abstract fun getArticlesDao(): ArticlesDao
}