package co.netguru.android.atstats.data.direct.model

import com.google.gson.annotations.SerializedName

data class DirectMessage(val user: String, val text: String, @SerializedName("ts") val timeStamp: String)