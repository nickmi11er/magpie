package ru.nickmiller.magpie.ui.feed

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.FragmentFeedBinding
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.ui.MainActivity


class BookmarksFragment : BaseFeedFragment<FragmentFeedBinding>() {
    override lateinit var swipeContainer: SwipeRefreshLayout
    override lateinit var recycler: RecyclerView
    lateinit var infoMsg: TextView

    override fun contentLayout(): Int = R.layout.fragment_feed

    override fun getTitleRes(): Int = R.string.title_bookmarks

    override val viewModel by viewModel<FeedViewModel> { mapOf("bookmarks" to true) }

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
        setupRefreshListener { viewModel.refresh() }
        viewModel.articles.observe(this, Observer {
            swipeContainer.isRefreshing = false
            it?.let { articles ->
                if (articles.isNotEmpty()) {
                    infoMsg.visibility = View.GONE
                    adapter.articles = articles as MutableList<Article>
                } else {
                    adapter.articles.clear()
                    infoMsg.text = "Your bookmarks will be shown here."
                    infoMsg.visibility = View.VISIBLE
                }
                adapter.notifyDataSetChanged()
            }
        })
    }
}