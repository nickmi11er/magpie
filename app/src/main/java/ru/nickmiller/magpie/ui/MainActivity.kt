package ru.nickmiller.magpie.ui

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.ActivityMainBinding
import ru.nickmiller.magpie.ui.base.BaseActivity
import ru.nickmiller.magpie.ui.channels.ChannelsFragment
import ru.nickmiller.magpie.ui.feed.FeedFragment
import ru.nickmiller.magpie.ui.settings.SettingsFragment

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun contentLayout(): Int = R.layout.activity_main
    override val viewModel by viewModel<MainViewModel>()
    lateinit var navView: BottomNavigationViewEx
    lateinit var titleView: TextView
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setupNavView()
    }

    private fun initViews() {
        navView = binding.bottomNavView
        titleView = binding.coordinator.findViewById(R.id.toolbar_title)
        toolbar = binding.coordinator.findViewById(R.id.toolbar)
    }

    private fun setupNavView() {
        navView.enableAnimation(false)
        navView.enableShiftingMode(false)
        navView.enableItemShiftingMode(false)
        navView.setTextVisibility(false)
        navView.setPaddingRelative(0, 16, 0, 16)

        navView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId != navView.selectedItemId) {
                when (item.itemId) {
                    //R.id.item_favor -> replaceFragment(R.id.main_container, favoriteFr)
                    R.id.item_search -> replaceFragment(R.id.main_container, ChannelsFragment::class)
                    R.id.item_settings -> replaceFragment(R.id.main_container, SettingsFragment::class)
                    else -> replaceFragment(R.id.main_container, FeedFragment::class)
                }
            } else {
                false
            }
        }
    }

    fun stopAppBarScrolling() {
        (toolbar.layoutParams as AppBarLayout.LayoutParams).scrollFlags = 0
    }

    fun startAppBarScrolling() {
        (toolbar.layoutParams as AppBarLayout.LayoutParams).scrollFlags =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
    }

    fun setTitle(title: String) {
        titleView.text = title
    }
}
