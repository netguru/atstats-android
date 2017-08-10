package co.netguru.android.socialslack.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics

// TODO 31.07.2017 Do the schema for the db
@Database(entities = arrayOf(ChannelStatistics::class, DirectChannelStatistics::class), version = 1, exportSchema = false)
abstract class SlackSocialDatabase : RoomDatabase() {

    companion object {
        val DB_NAME = "slack_social_db"
    }

    abstract fun channelsDao(): ChannelsDao

    abstract fun directChannelsDao(): DirectChannelsDao
}