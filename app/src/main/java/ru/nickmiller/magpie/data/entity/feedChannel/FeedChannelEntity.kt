package ru.nickmiller.magpie.data.entity.feedChannel

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class FeedChannelEntity(@PrimaryKey @SerializedName("feedId") var feedId: String,
                             @SerializedName("title") val title: String?,
                             @SerializedName("website") val website: String?,
                             @SerializedName("description") val description: String?,
                             @SerializedName("iconUrl") val iconUrl: String?,
                             @SerializedName("visualUrl") val visualUrl: String?,
                             var isSubscribed: Boolean = false) {

    override fun equals(other: Any?): Boolean = this.feedId == (other as FeedChannelEntity).feedId
    override fun hashCode(): Int = feedId.hashCode()
}