package co.netguru.android.socialslack.data.direct

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import io.reactivex.Single

@Dao
interface DirectChannelsDao {

    @Query("SELECT * FROM direct_channel_statistics WHERE channel_id = :channelId")
    fun getDirectChannelStatisticsByChannelId(channelId: String): Single<DirectChannelStatistics>

    @Query("SELECT * FROM direct_channel_statistics WHERE user_id = :userId")
    fun getDirectChannelStatisticsByUserId(userId: String): Single<DirectChannelStatistics>

    @Query("SELECT * FROM direct_channel_statistics")
    fun getAllDirectChannels(): Single<List<DirectChannelStatistics>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDirectChannel(directChannelStatistics: DirectChannelStatistics)

    @Query("DELETE FROM direct_channel_statistics")
    fun deleteAllDirectChannelsStatistics()
}