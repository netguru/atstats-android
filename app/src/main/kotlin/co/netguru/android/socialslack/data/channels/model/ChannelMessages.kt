package co.netguru.android.socialslack.data.channels.model

import com.google.gson.annotations.SerializedName


data class ChannelMessages(val type: String, @SerializedName("ts") val timeStamp: Float, val user: String, val text: String) {

    companion object {
        val MESSAGE_TYPE = "message"
        val HERE_TAG = "<!here>"
    }
}