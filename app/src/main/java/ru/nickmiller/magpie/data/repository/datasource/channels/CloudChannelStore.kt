package ru.nickmiller.magpie.data.repository.datasource.channels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import ru.magpie.magpie.data.net.NetHelper
import ru.nickmiller.magpie.data.entity.feedChannel.FeedChannelMapper
import ru.nickmiller.magpie.model.FeedChannel
import java.io.IOException


class CloudChannelStore(val netHelper: NetHelper, val mapper: FeedChannelMapper) {
    private val FEEDLY_API_SEARCH_FEED = "https://cloud.feedly.com/v3/search/feeds?n=10&q="

    fun getChannelsList(keyword: String): LiveData<List<FeedChannel>> {
        val data = MutableLiveData<List<FeedChannel>>()

        netHelper.call(FEEDLY_API_SEARCH_FEED + keyword).enqueue(object : Callback {
            override fun onResponse(call: Call?, resp: Response?) {
                if (resp != null && resp.isSuccessful) {
                    data.postValue(mapper.jsonToFeedChannelCollection(resp.body()?.string()
                            ?: throw IllegalArgumentException("Can not parse json string")))
                }
            }

            override fun onFailure(call: Call?, e: IOException) {

            }
        })
        return data
    }

    fun getTopicChanels(title: String): LiveData<List<FeedChannel>> {
        val ld = MutableLiveData<List<FeedChannel>>()
        FirebaseDatabase.getInstance().reference.child("Topics").child("data").child(title).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val channels = mutableListOf<FeedChannel>()
                for (channel in p0.children) {
                    channels.add(channel.getValue(FeedChannel::class.java) ?: continue)
                }
                ld.postValue(channels)
            }
        })
        return ld
    }

}