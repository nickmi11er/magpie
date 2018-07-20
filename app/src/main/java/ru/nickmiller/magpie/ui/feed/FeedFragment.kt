package ru.nickmiller.magpie.ui.feed

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.FragmentFeedBinding
import ru.nickmiller.magpie.ui.MainActivity
import ru.nickmiller.magpie.utils.FetchStatus
import ru.nickmiller.magpie.utils.mainLog


class FeedFragment : BaseFeedFragment<FragmentFeedBinding>() {
    override lateinit var swipeContainer: SwipeRefreshLayout
    override lateinit var recycler: RecyclerView
    lateinit var infoMsg: TextView

    override fun contentLayout(): Int = R.layout.fragment_feed

    override fun getTitleRes(): Int = R.string.title_feed

    override val viewModel by viewModel<FeedViewModel> { mapOf("bookmarks" to false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initHandlers()
        viewModel.refresh()
    }

    private fun initViews() {
        (activity as MainActivity).startAppBarScrolling()
        recycler = binding.feedRecycler
        swipeContainer = binding.swipeContainer
        infoMsg = binding.infoMsg
        adapter = ArticlesAdapter()
        adapter.articleClickListener = viewModel.clickListener
        setUpRecycler()
    }

    private fun initHandlers() {
        setupRefreshListener { viewModel.refresh(true) }
        viewModel.articles.observe(this, Observer {
            it?.let {
                adapter.addArticles(it)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.fetchStatus.observe(this, Observer {
            it?.let {
                when(it.status) {
                    FetchStatus.PROGRESS -> {
                        mainLog("ALL: ${it.all}    COMPLETED: ${it.completed}     ERRORS: ${it.errors}")
                    }
                    FetchStatus.STARTED -> {
                        (activity as MainActivity).setTitle("Updating...")
                    }
                    FetchStatus.COMPLETED -> {
                        setTitle()
                        swipeContainer.isRefreshing = false
                        if (it.all == 0) {
                            infoMsg.text = "This is your feed. Subscribe some channels to read any articles."
                            infoMsg.visibility = View.VISIBLE
                        } else {
                            infoMsg.visibility = View.GONE
                        }
                    }
                }
            }
        })
    }
}