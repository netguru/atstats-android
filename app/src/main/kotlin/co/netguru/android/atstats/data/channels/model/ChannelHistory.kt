package co.netguru.android.atstats.data.channels.model

import com.google.gson.annotations.SerializedName


data class ChannelHistory (@SerializedName("ok") val isSuccessful: Boolean,
                           @SerializedName("messages") val messageList: List<ChannelMessage>,
                           @SerializedName("has_more") val hasMore: Boolean)