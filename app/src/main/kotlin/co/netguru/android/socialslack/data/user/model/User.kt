package co.netguru.android.socialslack.data.user.model

import com.google.gson.annotations.SerializedName

data class User(val id: String,
                @SerializedName("name") val username: String?,
                @SerializedName("first_name") val firstName: String?,
                @SerializedName("last_name") val lastName: String?,
                @SerializedName("real_name") val realName: String?,
                @SerializedName("deleted") val isDeleted: Boolean,
                val profile: UserProfile)