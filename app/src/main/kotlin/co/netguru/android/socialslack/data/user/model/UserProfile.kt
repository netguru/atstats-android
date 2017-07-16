package co.netguru.android.socialslack.data.user.model

import com.google.gson.annotations.SerializedName

data class UserProfile(@SerializedName("first_name") val firstName: String,
                       @SerializedName("last_name") val lastName: String)