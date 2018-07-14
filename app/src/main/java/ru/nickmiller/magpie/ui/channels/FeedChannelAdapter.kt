package ru.nickmiller.magpie.ui.channels

import android.databinding.DataBindingUtil
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.ChannelItemBinding
import ru.nickmiller.magpie.model.FeedChannel


class FeedChannelAdapter : RecyclerView.Adapter<FeedChannelAdapter.ChannelHolder>() {
    var channels = mutableListOf<FeedChannel>()
    private var onChannelSubListener: ((action: ChActionType, channel: FeedChannel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ChannelHolder(DataBindingUtil.inflate(inflater, R.layout.channel_item, parent, false))
    }

    override fun getItemCount(): Int = channels.size

    override fun onBindViewHolder(holder: ChannelHolder, position: Int) {
        holder.bind(channels[position])
        holder.binding.moreBtn.setOnClickListener { showPopup(it, channels[position]) }
    }

    class ChannelHolder(val binding: ChannelItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(channel: FeedChannel) {
            binding.channel = channel
            binding.executePendingBindings()
        }
    }

    private fun showPopup(view: View, channel: FeedChannel) {
        val context = view.context
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.fc_popup_menu)
        popup.menu.findItem(R.id.fc_subscribe).title =
                if (channel.isSubscribed) "Unsubscribe" else "Subscribe"
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.fc_subscribe -> {
                    val action = if (!channel.isSubscribed) ChActionType.SUBSCRIBE else ChActionType.UNSUBSCRIBE
                    onChannelSubListener?.invoke(action, channel)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    fun setOnChannelSubListener(subListener: (action: ChActionType, channel: FeedChannel) -> Unit) {
        this.onChannelSubListener = subListener
    }

    enum class ChActionType {
        SUBSCRIBE,
        UNSUBSCRIBE,
        OPEN_URL
    }

}