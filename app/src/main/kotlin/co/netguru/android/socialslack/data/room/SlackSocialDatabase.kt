package co.netguru.android.socialslack.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelMessage

@Database(entities = arrayOf(Channel::class, ChannelMessage::class), version = 1)
abstract class SlackSocialDatabase : RoomDatabase() {

    companion object {
        val DB_NAME = "slack_social_db"
    }

    abstract fun channelsDao(): ChannelsDao
}