package co.netguru.android.socialslack.data.session.model

import com.google.gson.annotations.SerializedName

data class Token(@SerializedName("access_token") val accessToken: String,
                 val scope: String, @SerializedName("team_id") val teamId: String)