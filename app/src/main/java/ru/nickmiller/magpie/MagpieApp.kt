package ru.nickmiller.magpie

import android.app.Application
import android.arch.persistence.room.Room
import org.koin.android.ext.android.startKoin
import ru.nickmiller.magpie.data.cache.AppDatabase
import ru.nickmiller.magpie.di.articleModule
import ru.nickmiller.magpie.di.channelsModule
import ru.nickmiller.magpie.di.feedModule
import ru.nickmiller.magpie.di.serachChannelModule


class MagpieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "magpie_db")
                .fallbackToDestructiveMigration()
                .build()
        startKoin(this, listOf(channelsModule,
                serachChannelModule,
                feedModule,
                articleModule))
    }

    companion object {
        lateinit var database: AppDatabase
    }
}