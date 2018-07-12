package ru.nickmiller.magpie.ui.search

import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.ActivitySearchBinding

import ru.nickmiller.magpie.ui.base.BaseActivity

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>()  {
    override fun contentLayout(): Int = R.layout.activity_search

    override val viewModel by viewModel<SearchViewModel>()
}
