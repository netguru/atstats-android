package co.netguru.android.socialslack.data.channels.model

import com.google.gson.annotations.SerializedName


data class ChannelHistoryRequest(val token: String,
                                 val channel: String,
                                 val count: Int,
                                 val inclusive: Boolean,
                                 val latest: Float,
                                 val oldest: Float,
                                 @SerializedName("unreads") val unReads: Boolean)