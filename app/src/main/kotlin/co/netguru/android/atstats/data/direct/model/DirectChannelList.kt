package co.netguru.android.atstats.data.direct.model

import com.google.gson.annotations.SerializedName

data class DirectChannelList(@SerializedName("ok") val isSuccessful: Boolean,
                             @SerializedName("ims") val channels: List<DirectChannel>)