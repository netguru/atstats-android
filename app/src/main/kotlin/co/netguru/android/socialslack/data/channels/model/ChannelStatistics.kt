package co.netguru.android.socialslack.data.channels.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "channel_statistics")
data class ChannelStatistics(@PrimaryKey @ColumnInfo(name = "channel_id") var channelId: String,
                             var channelName: String,
                             var messageCount: Int,
                             var hereCount: Int,
                             var mentionsCount: Int,
                             var myMessageCount: Int)