package co.netguru.android.atstats.data.user.profile.model

import com.google.gson.annotations.SerializedName

enum class Presence {

    @SerializedName("active")
    ACTIVE,
    @SerializedName("away")
    AWAY;
}