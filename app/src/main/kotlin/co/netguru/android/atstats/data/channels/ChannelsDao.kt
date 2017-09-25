package co.netguru.android.atstats.data.channels

import android.arch.persistence.room.*
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import io.reactivex.Single


@Dao
interface ChannelsDao {

    @Query("SELECT * FROM channel_statistics WHERE channel_id = :channelId")
    fun getChannelById(channelId: String): Single<ChannelStatistics>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChannel(channel: ChannelStatistics)

    @Query("SELECT * FROM channel_statistics")
    fun getAllChannels (): Single<List<ChannelStatistics>>

    @Query("DELETE FROM channel_statistics")
    fun deleteAllChannels()
}