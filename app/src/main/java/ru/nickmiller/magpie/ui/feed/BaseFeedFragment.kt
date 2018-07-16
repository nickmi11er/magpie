package ru.nickmiller.magpie.ui.feed

import android.arch.lifecycle.AndroidViewModel
import android.databinding.ViewDataBinding
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.ui.base.BaseFragment


abstract class BaseFeedFragment<B : ViewDataBinding, M : AndroidViewModel> : BaseFragment<B, M>() {
    abstract var swipeContainer: SwipeRefreshLayout
    abstract var recycler: RecyclerView
    lateinit var adapter: ArticlesAdapter

    protected fun setUpRecycler() {
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.itemAnimator = FadeInUpAnimator()
        recycler.itemAnimator.addDuration = 100
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary)
        recycler.adapter = SlideInBottomAnimationAdapter(adapter)
        recycler.adapter = adapter
    }

    fun setupRefreshListener(listener: () -> Unit) = swipeContainer.setOnRefreshListener(listener)
}