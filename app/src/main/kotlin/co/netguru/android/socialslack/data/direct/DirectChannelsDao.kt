package co.netguru.android.socialslack.data.direct

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import io.reactivex.Single

@Dao
interface DirectMessagesDao {

    @Query("SELECT * FROM direct_channel_statistics WHERE channel_id = :channelId")
    fun getDirectMessageByChannelId(channelId: String): Single<DirectChannelStatistics>

    @Query("SELECT * FROM direct_channel_statistics WHERE channel_id = :channelId")
}