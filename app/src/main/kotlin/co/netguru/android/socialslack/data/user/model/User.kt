package co.netguru.android.socialslack.data.user.model

import com.google.gson.annotations.SerializedName

data class User(val id: String,
                @SerializedName("name") val username: String,
                @SerializedName("real_name") val realName: String,
                val profile: UserProfile)