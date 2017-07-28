package co.netguru.android.socialslack.data.channels.model

import com.google.gson.annotations.SerializedName


data class ChannelHistory (@SerializedName("ok") val isSuccessful: Boolean,
                           val latest: Float,
                           @SerializedName("messages") val messageList: List<ChannelMessages>,
                           @SerializedName("has_more") val hasMore: Boolean)