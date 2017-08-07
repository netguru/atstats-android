package co.netguru.android.socialslack.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics

// TODO 31.07.2017 Do the schema for the db
@Database(entities = arrayOf(ChannelStatistics::class), version = 1, exportSchema = false)
abstract class SlackSocialDatabase : RoomDatabase() {

    companion object {
        val DB_NAME = "slack_social_db"
    }

    abstract fun channelsDao(): ChannelsDao
}