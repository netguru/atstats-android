package co.netguru.android.socialslack.data.user.model

import com.google.gson.annotations.SerializedName

data class UserResponse(@SerializedName("ok") val isSuccessful: Boolean,
                        val user: User)