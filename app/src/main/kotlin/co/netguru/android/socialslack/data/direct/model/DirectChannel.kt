package co.netguru.android.socialslack.data.direct.model

import com.google.gson.annotations.SerializedName

data class DirectChannel(@SerializedName("id") val id: String,
                         @SerializedName("user") val userId: String,
                         @SerializedName("created") val created: Long)