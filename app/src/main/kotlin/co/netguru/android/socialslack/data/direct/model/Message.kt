package co.netguru.android.socialslack.data.direct.model

import com.google.gson.annotations.SerializedName

data class Message(val user: String?, val text: String, @SerializedName("ts") val timeStamp: String)