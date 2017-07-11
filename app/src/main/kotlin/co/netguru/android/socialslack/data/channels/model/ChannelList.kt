package co.netguru.android.socialslack.data.channels.model

import com.google.gson.annotations.SerializedName

data class ChannelList(@SerializedName("ok") val isSuccessful: Boolean,
                       @SerializedName("channels") val channelList: List<Channel>)