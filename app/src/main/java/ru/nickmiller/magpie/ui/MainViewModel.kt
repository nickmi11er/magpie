package ru.nickmiller.magpie.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import ru.nickmiller.magpie.utils.mainLog


class MainViewModel(app: Application) : AndroidViewModel(app) {
    init {
        mainLog("MainViewModel was created")
    }

    override fun onCleared() {
        mainLog("MainViewModel on cleared")
        super.onCleared()
    }
}