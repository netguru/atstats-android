package co.netguru.android.socialslack.data.direct.model

import com.google.gson.annotations.SerializedName

data class DirectChannelsHistory(@SerializedName("ok") val isSuccessful: Boolean,
                                 @SerializedName("latest") val latest: String,
                                 @SerializedName("messages") val messages: List<DirectMessage>,
                                 @SerializedName("has_more") val hasMore: Boolean)