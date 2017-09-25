package co.netguru.android.atstats.data.session.model

import com.google.gson.annotations.SerializedName

data class Token(@SerializedName("access_token") val accessToken: String,
                 val scope: String)