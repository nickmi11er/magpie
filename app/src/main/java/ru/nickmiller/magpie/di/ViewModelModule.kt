package ru.nickmiller.magpie.di

import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import ru.magpie.magpie.data.net.NetHelper
import ru.nickmiller.magpie.MagpieApp
import ru.nickmiller.magpie.data.cache.AppDatabase
import ru.nickmiller.magpie.data.cache.dao.ChannelsDao
import ru.nickmiller.magpie.data.entity.feedChannel.FeedChannelMapper
import ru.nickmiller.magpie.data.repository.FeedChannelRepository
import ru.nickmiller.magpie.data.repository.datasource.channels.CloudChannelStore
import ru.nickmiller.magpie.data.repository.datasource.channels.LocalChannelStore
import ru.nickmiller.magpie.ui.channels.ChannelsViewModel
import ru.nickmiller.magpie.ui.search.SearchViewModel

val serachChannelModule = applicationContext {
    viewModel { SearchViewModel(androidApplication(), get()) }
    bean { FeedChannelRepository(get(), get(), get()) }
    bean { FeedChannelMapper() }
    bean {
        MagpieApp.database
    }
    bean { get<AppDatabase>().getChannelDao() }
    bean { LocalChannelStore(get(), get()) }
    bean { CloudChannelStore(get(), get()) }
    bean { NetHelper() }
}

val channelsModule = applicationContext {
    viewModel { ChannelsViewModel(androidApplication()) }
}
 
 
