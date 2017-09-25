package co.netguru.android.atstats.data.direct.model

import com.google.gson.annotations.SerializedName

data class DirectChannel(@SerializedName("id") val id: String,
                         @SerializedName("user") val userId: String,
                         @SerializedName("created") val created: Long,
                         @SerializedName("is_user_deleted") val isUserDeleted: Boolean)