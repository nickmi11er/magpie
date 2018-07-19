package ru.nickmiller.magpie.di

import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import ru.magpie.magpie.data.net.NetHelper
import ru.nickmiller.magpie.MagpieApp
import ru.nickmiller.magpie.data.cache.AppDatabase
import ru.nickmiller.magpie.data.entity.article.ArticleMapper
import ru.nickmiller.magpie.data.entity.feed.FeedMapper
import ru.nickmiller.magpie.data.entity.feedChannel.FeedChannelMapper
import ru.nickmiller.magpie.data.repository.ArticlesRepository
import ru.nickmiller.magpie.data.repository.FeedChannelRepository
import ru.nickmiller.magpie.data.repository.datasource.articles.CloudArticlesStore
import ru.nickmiller.magpie.data.repository.datasource.articles.LocalArticlesStore
import ru.nickmiller.magpie.data.repository.datasource.channels.CloudChannelStore
import ru.nickmiller.magpie.data.repository.datasource.channels.LocalChannelStore
import ru.nickmiller.magpie.ui.article.ArticleViewModel
import ru.nickmiller.magpie.ui.channels.ChannelsViewModel
import ru.nickmiller.magpie.ui.feed.FeedViewModel
import ru.nickmiller.magpie.ui.channels.SearchViewModel

val serachChannelModule = applicationContext {
    viewModel { SearchViewModel(androidApplication(), get()) }
    bean { FeedChannelRepository(get(), get(), get()) }
    bean { FeedChannelMapper() }
    bean { MagpieApp.database }
    bean { get<AppDatabase>().getChannelDao() }
    bean { LocalChannelStore(get(), get(), get()) }
    bean { CloudChannelStore(get(), get()) }
    bean { NetHelper() }
}

val channelsModule = applicationContext {
    viewModel { ChannelsViewModel(androidApplication(), get()) }
}

val feedModule = applicationContext {
    viewModel { FeedViewModel(androidApplication(), get(), it["bookmarks"] ?: false) }
    bean { ArticlesRepository(get(), get()) }
    bean { LocalArticlesStore(get(), get()) }
    bean { get<AppDatabase>().getArticlesDao() }
    bean { CloudArticlesStore(get(), get(), get(), get()) }
    bean { ArticleMapper() }
    bean { FeedMapper(get()) }
}

val articleModule = applicationContext {
    viewModel { ArticleViewModel(androidApplication(), get()) }
}
 
 
