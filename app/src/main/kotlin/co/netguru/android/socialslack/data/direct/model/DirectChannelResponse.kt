package co.netguru.android.socialslack.data.direct.model

import com.google.gson.annotations.SerializedName

data class DirectChannelResponse(@SerializedName("ok") val isSuccessful: Boolean,
                                 @SerializedName("ims") val channels: List<DirectChannel>)