package ru.nickmiller.magpie.ui.channels

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.SuggestedItemBinding
import ru.nickmiller.magpie.model.Topic

class TopicsAdapter : RecyclerView.Adapter<TopicsAdapter.SuggestedHolder>() {
    val topics = mutableListOf(
            Topic(1, "News", R.drawable.ic_newspaper, R.color.colorRedSoft),
            Topic(2, "Sport", R.drawable.ic_sport, R.color.colorGreenSoft),
            Topic(3, "Tech", R.drawable.ic_mobile_phone, R.color.colorYellowSoft),
            Topic(4, "Business", R.drawable.ic_chat, R.color.colorBlueSoft),
            Topic(5, "Politics", R.drawable.ic_mail_box, R.color.colorOrangeSoft),
            Topic(7, "Gaming", R.drawable.ic_paper_plane, R.color.colorVioletSoft))
    var onTopickClick: ((topic: Topic) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SuggestedHolder(DataBindingUtil.inflate(inflater, R.layout.suggested_item, parent, false))
    }


    override fun getItemCount(): Int = topics.size

    override fun onBindViewHolder(holder: SuggestedHolder, position: Int) {
        with(holder) {
            bind(topics[position])
            holder.binding.iconSuggested.setOnClickListener { onTopickClick?.invoke(topics[position]) }
        }
    }

    class SuggestedHolder(val binding: SuggestedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.topic = topic
            binding.executePendingBindings()
        }
    }

}
 
 
