package co.netguru.android.socialslack.data.channels.model

import com.google.gson.annotations.SerializedName

data class ChannelMessage(val type: String, @SerializedName("ts") val timeStamp: String, val user: String, val text: String) {

    companion object {
        const val MESSAGE_TYPE = "message"
        const val HERE_TAG = "<!here"
        const val USER_MENTION = "<@%s>"
    }
}