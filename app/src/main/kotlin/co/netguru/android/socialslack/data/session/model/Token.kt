package co.netguru.android.socialslack.data.session.model

import com.google.gson.annotations.SerializedName

data class Token(@SerializedName("access_token") val accessToken: String,
                 val scope: String,
                 @SerializedName("installer_user_id") val userId: String,
                 @SerializedName("team_id") val teamId: String)