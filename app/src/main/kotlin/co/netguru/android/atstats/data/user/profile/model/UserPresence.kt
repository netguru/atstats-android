package co.netguru.android.atstats.data.user.profile.model

import com.google.gson.annotations.SerializedName

data class UserPresence(@SerializedName("ok") val isSuccessful: Boolean,
                        val presence: Presence)