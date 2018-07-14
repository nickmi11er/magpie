package ru.nickmiller.magpie.ui.channels

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.ChannelItemBinding
import ru.nickmiller.magpie.model.FeedChannel


class FeedChannelAdapter : RecyclerView.Adapter<FeedChannelAdapter.ChannelHolder>() {
    var channels = listOf<FeedChannel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ChannelHolder(DataBindingUtil.inflate(inflater, R.layout.channel_item, parent, false))
    }

    override fun getItemCount(): Int = channels.size

    override fun onBindViewHolder(holder: ChannelHolder, position: Int) = holder.bind(channels[position])

    class ChannelHolder(val binding: ChannelItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(channel: FeedChannel) {
            binding.channel = channel
            binding.executePendingBindings()
        }
    }

}