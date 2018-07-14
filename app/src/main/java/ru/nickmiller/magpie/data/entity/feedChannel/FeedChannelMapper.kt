package ru.nickmiller.magpie.data.entity.feedChannel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import ru.nickmiller.magpie.model.FeedChannel


class FeedChannelMapper {

    fun transform(entity: FeedChannelEntity?): FeedChannel? =
            if (entity != null) {
                with(entity) {
                    FeedChannel(feedId, title, website, description, iconUrl, visualUrl, isSubscribed)
                }
            } else null


    fun transform(entities: List<FeedChannelEntity>): MutableList<FeedChannel> {
        val feedChannels = mutableListOf<FeedChannel>()
        for (entity in entities) {
            feedChannels.add(transform(entity)!!)
        }
        return feedChannels
    }

    fun map(model: FeedChannel?): FeedChannelEntity? =
            if (model != null) {
                with(model) {
                    FeedChannelEntity(feedId!!, title, website, description, iconUrl, visualUrl, isSubscribed)
                }
            } else null

    private val FEEDLY_RESULTS = "results"


    fun transformFCEntityCollection(jsonObj: String): MutableList<FeedChannelEntity> {
        val respJson = JSONObject(jsonObj)
        val results = respJson.getJSONArray(FEEDLY_RESULTS)
        val listType = object : TypeToken<List<FeedChannelEntity>>() {}.type
        return Gson().fromJson(results.toString(), listType)
    }

    fun jsonToFeedChannelCollection(jsonObj: String) =
            transform(transformFCEntityCollection(jsonObj))

}