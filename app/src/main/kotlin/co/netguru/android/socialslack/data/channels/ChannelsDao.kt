package co.netguru.android.socialslack.data.channels

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import io.reactivex.Single


@Dao
interface ChannelsDao {

    @Query("SELECT * FROM channel_statistics WHERE channel_id = :channelId")
    fun getChannelById(channelId: String): Single<ChannelStatistics>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChannel(channel: ChannelStatistics)

    @Query("SELECT * FROM channel_statistics")
    fun getAllChannels (): Single<List<ChannelStatistics>>
}