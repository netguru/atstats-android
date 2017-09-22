package co.netguru.android.socialslack.data.direct.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "direct_channel_statistics")
data class DirectChannelStatistics(@PrimaryKey @ColumnInfo(name = "channel_id") var channelId: String,
                                   @ColumnInfo(name = "user_id") var userId: String,
                                   var messagesFromUs: Int,
                                   var messagesFromOtherUser: Int,
                                   var streakDays: Int,
                                   var isCurrentUser: Boolean)