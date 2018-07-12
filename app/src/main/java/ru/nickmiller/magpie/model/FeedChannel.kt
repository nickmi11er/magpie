package ru.nickmiller.magpie.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class FeedChannel(val feedId: String? = null,
                       val title: String? = null,
                       val website: String? = null,
                       val description: String? = null,
                       val iconUrl: String? = null,
                       val visualUrl: String? = null,
                       var isSubscribed: Boolean = false) : Parcelable