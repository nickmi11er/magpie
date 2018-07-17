package ru.nickmiller.magpie.ui.channels

import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.ChannelItemBinding
import ru.nickmiller.magpie.model.FeedChannel
import ru.nickmiller.magpie.model.Topic


class FeedChannelAdapter(val searchFeed: Boolean = false) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val HEADER_ITEM_VIEW = 1
    private val ITEM_VIEW = 2
    var channels = mutableListOf<FeedChannel>()
    private var onChannelSubListener: ((action: ChActionType, channel: FeedChannel?, topic: Topic?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == HEADER_ITEM_VIEW && searchFeed) SuggestedHolder(inflater.inflate(R.layout.suggested_recycler_view, parent, false))
        else ChannelHolder(DataBindingUtil.inflate(inflater, R.layout.channel_item, parent, false))
    }

    override fun getItemCount(): Int = channels.size + if (searchFeed) 1 else 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ChannelHolder) {
            holder.bind(channels[position - if (searchFeed) 1 else 0])
            holder.binding.moreBtn.setOnClickListener { showPopup(it, channels[position - if (searchFeed) 1 else 0]) }
        } else if (holder is SuggestedHolder) {
            holder.adapter.onTopickClick = { onChannelSubListener?.invoke(ChActionType.OPEN_TOPIC, null, it) }
        }
    }

    class ChannelHolder(val binding: ChannelItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(channel: FeedChannel) {
            binding.channel = channel
            binding.executePendingBindings()
        }
    }

    class SuggestedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recycler: RecyclerView = itemView.findViewById(R.id.suggested_recycler)
        val adapter = TopicsAdapter()

        init {
            recycler.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            recycler.itemAnimator = FadeInUpAnimator()
            recycler.itemAnimator.addDuration = 100
            recycler.adapter = adapter
        }
    }

    override fun getItemViewType(position: Int): Int =
            if (position == 0) HEADER_ITEM_VIEW
            else ITEM_VIEW

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
                    onChannelSubListener?.invoke(action, channel, null)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    fun setOnChannelSubListener(subListener: (action: ChActionType, channel: FeedChannel?, topic: Topic?) -> Unit) {
        this.onChannelSubListener = subListener
    }

    enum class ChActionType {
        SUBSCRIBE,
        UNSUBSCRIBE,
        OPEN_URL,
        OPEN_TOPIC
    }

}