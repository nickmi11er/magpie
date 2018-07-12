package ru.nickmiller.magpie.ui.channels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import android.view.View
import ru.nickmiller.magpie.ui.search.SearchActivity


class ChannelsViewModel(val app: Application) : AndroidViewModel(app) {
    fun onSearchChannel(view: View) = app.startActivity(Intent(app, SearchActivity::class.java))
}