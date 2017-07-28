package co.netguru.android.socialslack.data.user.model

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(@SerializedName("ok") val isResponseValid: Boolean,
                               @SerializedName("profile") val userProfile: UserProfile)