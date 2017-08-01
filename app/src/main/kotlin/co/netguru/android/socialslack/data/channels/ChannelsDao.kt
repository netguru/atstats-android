package co.netguru.android.socialslack.data.channels

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelMessage
import io.reactivex.Flowable


@Dao
interface ChannelsDao {

    @Query("SELECT * FROM channel_message WHERE channel_id = :channelId AND time_stamp >= :afterTimeStamp ORDER BY time_stamp ASC")
    fun getLatestMessagesForChannel(channelId: String, afterTimeStamp: Float): Flowable<ChannelMessage>

    @Insert
    fun insertMessage(channelMessage: ChannelMessage)

    @Insert
    fun insertChannel(channel: Channel)

    @Query("SELECT * FROM channel")
    fun getAllChannels (): Flowable<Channel>
}