package ru.nickmiller.magpie

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.nickmiller.magpie.di.channelsModule


class MagpieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(channelsModule))
    }
}