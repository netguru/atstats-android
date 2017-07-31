package co.netguru.android.socialslack.data.channels.model

import android.arch.persistence.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "channel_message",
        indices = arrayOf(Index(value = "channel_id")),
        foreignKeys = arrayOf(ForeignKey(entity = Channel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("channel_id"),
        onDelete = ForeignKey.CASCADE)))
data class ChannelMessage(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "message_id") var messageId: Int = 0,
                          var type: String = "",
                          @ColumnInfo(name = "channel_id") var channelId: String = "",
                          @SerializedName("ts") @ColumnInfo(name = "time_stamp") var timeStamp: Float = 0F,
                          var user: String = "",
                          var text: String = "") {

    companion object {
        @Ignore val MESSAGE_TYPE = "message"
        @Ignore val HERE_TAG = "<!here>"
    }
}