package co.netguru.android.atstats.data.user.model

import com.google.gson.annotations.SerializedName

data class UserResponse(@SerializedName("ok") val isSuccessful: Boolean,
                        val user: User)