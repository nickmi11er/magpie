package ru.nickmiller.magpie.ui

import android.os.Bundle
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun contentLayout(): Int = R.layout.activity_main
    override val viewModel by viewModel<MainViewModel>()
    lateinit var navView: BottomNavigationViewEx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setupNavView()
    }

    private fun initViews() {
        navView = binding.bottomNavView
    }

    private fun setupNavView() {
        navView.enableAnimation(false)
        navView.enableShiftingMode(false)
        navView.enableItemShiftingMode(false)
        navView.setTextVisibility(false)
        navView.setPaddingRelative(0, 16, 0, 16)

        navView.setOnNavigationItemSelectedListener { item ->
            if (item.itemId != navView.selectedItemId) {
//                when (item.itemId) {
//                    R.id.item_favor -> replaceFragment(R.id.main_container, favoriteFr)
//                    R.id.item_search -> replaceFragment(R.id.main_container, channelsFragment)
//                    R.id.item_settings -> replaceFragment(R.id.main_container, settingsFragment)
//                    else -> replaceFragment(R.id.main_container, feedFr)
//                }
                true
            } else {
                false
            }
        }
    }
}
