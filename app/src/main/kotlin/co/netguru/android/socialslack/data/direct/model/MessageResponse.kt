package co.netguru.android.socialslack.data.direct.model

import com.google.gson.annotations.SerializedName

data class MessageResponse(@SerializedName("ok") val isSuccessful: Boolean,
                           @SerializedName("messages") val messages: List<Message>)