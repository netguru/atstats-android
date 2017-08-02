package co.netguru.android.socialslack.data.channels.model

import android.arch.persistence.room.Ignore
import com.google.gson.annotations.SerializedName

data class ChannelMessage(val type: String, @SerializedName("ts") val timeStamp: Float, val user: String, val text: String) {

    companion object {
        @Ignore val MESSAGE_TYPE = "message"
        @Ignore val HERE_TAG = "<!here"
    }
}