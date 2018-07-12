package ru.nickmiller.magpie.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.ui.MainActivity


class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).setTitle("Settings")
        (activity as MainActivity).stopAppBarScrolling()
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref)

    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
    }
}