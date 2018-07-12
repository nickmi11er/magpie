package ru.nickmiller.magpie.di

import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import ru.nickmiller.magpie.ui.channels.ChannelsViewModel

val serachChannelModule = applicationContext {

}

val channelsModule = applicationContext {
    viewModel { ChannelsViewModel(androidApplication()) }
}
 
 
