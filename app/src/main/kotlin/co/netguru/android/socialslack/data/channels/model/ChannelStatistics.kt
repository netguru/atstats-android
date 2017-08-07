package co.netguru.android.socialslack.data.channels.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import co.netguru.android.socialslack.data.share.Sharable
import paperparcel.PaperParcel

@PaperParcel @Entity(tableName = "channel_statistics")
data class ChannelStatistics(@PrimaryKey @ColumnInfo(name = "channel_id") var channelId: String,
                             var channelName: String,
                             var messageCount: Int,
                             var hereCount: Int,
                             var mentionsCount: Int,
                             var myMessageCount: Int):Parcelable, Sharable {

    var currentPositionInList: Int = 0

    companion object {
        @JvmField val CREATOR = PaperParcelChannelStatistics.CREATOR
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelChannelStatistics.writeToParcel(this, dest, flags)
    }

    override fun describeContents() = 0
}