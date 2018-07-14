package ru.nickmiller.magpie.ui.search

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pnikosis.materialishprogress.ProgressWheel
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.ActivitySearchBinding


import ru.nickmiller.magpie.ui.base.BaseActivity
import ru.nickmiller.magpie.ui.channels.FeedChannelAdapter

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {
    lateinit var clearBtn: View
    lateinit var recycler: RecyclerView
    lateinit var adapter: FeedChannelAdapter
    lateinit var progress: ProgressWheel

    override fun contentLayout(): Int = R.layout.activity_search

    override val viewModel by viewModel<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        initViews()
        initHandlers()
    }

    private fun initHandlers() {
        viewModel.searchText.observe(this, Observer {
            clearBtn.visibility = if (it!!.isNotEmpty()) View.VISIBLE else View.GONE
        })
        viewModel.searchAction.observe(this, Observer {
            hideKeyboard()
            progress.spin()
        })
        viewModel.channels.observe(this, Observer {
            progress.stopSpinning()
            it.let {
                adapter.channels = it!!
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun initViews() {
        clearBtn = binding.clearBtn
        progress = binding.progressView
        progress.stopSpinning()
        recycler = binding.searchRecycler
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.itemAnimator = FadeInUpAnimator()
        recycler.itemAnimator.addDuration = 100
        adapter = FeedChannelAdapter()
        recycler.adapter = adapter
    }

    fun clearText(view: View) = binding.searchText.text.clear()

    fun onBackArrowPressed(view: View) = onBackPressed()
}
