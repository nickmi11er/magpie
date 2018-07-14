package ru.nickmiller.magpie.ui.channels

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.FragmentChannelsBinding
import ru.nickmiller.magpie.model.FeedChannel
import ru.nickmiller.magpie.ui.MainActivity
import ru.nickmiller.magpie.ui.base.BaseFragment


class ChannelsFragment : BaseFragment<FragmentChannelsBinding, ChannelsViewModel>() {
    lateinit var infoMsg: TextView
    lateinit var recycler: RecyclerView
    lateinit var adapter: FeedChannelAdapter

    override fun contentLayout(): Int = R.layout.fragment_channels

    override fun getTitleRes(): Int = R.string.title_channels

    override val viewModel by viewModel<ChannelsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).stopAppBarScrolling()
        binding.viewModel = viewModel
        initViews()
        initHandlers()
    }

    private fun initHandlers() {
        viewModel.channels.observe(this, Observer {
            if (it?.isEmpty() != false) {
                adapter.channels.clear()
                infoMsg.text = "There are no any channels yet. Add some to see a feed of a channel."
                infoMsg.visibility = View.VISIBLE
            } else {
                infoMsg.visibility = View.GONE
                adapter.channels = it as MutableList<FeedChannel>
            }
            adapter.notifyDataSetChanged()
        })
        adapter.setOnChannelSubListener { action, channel -> viewModel.onChannelClick(action, channel) }
    }

    private fun initViews() {
        infoMsg = binding.infoMsg
        recycler = binding.channelsRecycler
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.itemAnimator = FadeInUpAnimator()
        recycler.itemAnimator.addDuration = 100
        adapter = FeedChannelAdapter()
        recycler.adapter = adapter
    }
}