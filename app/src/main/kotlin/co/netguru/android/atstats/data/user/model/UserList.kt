package co.netguru.android.atstats.data.user.model

import com.google.gson.annotations.SerializedName

data class UserList(@SerializedName("ok") val isSuccessful: Boolean,
                    @SerializedName("members") val usersList: List<User>)