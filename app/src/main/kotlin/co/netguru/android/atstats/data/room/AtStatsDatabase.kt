package co.netguru.android.atstats.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import co.netguru.android.atstats.data.channels.ChannelsDao
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.direct.DirectChannelsDao
import co.netguru.android.atstats.data.direct.model.DirectChannelStatistics
import co.netguru.android.atstats.data.team.TeamDao
import co.netguru.android.atstats.data.team.model.Team
import co.netguru.android.atstats.data.user.UsersDao
import co.netguru.android.atstats.data.user.model.UserDB

@Database(
        entities = arrayOf(ChannelStatistics::class, DirectChannelStatistics::class, Team::class, UserDB::class),
        version = 6)
abstract class AtStatsDatabase : RoomDatabase() {

    companion object {
        val DB_NAME = "atstats_db"
    }

    abstract fun channelsDao(): ChannelsDao

    abstract fun directChannelsDao(): DirectChannelsDao

    abstract fun teamDao(): TeamDao

    abstract fun usersDao(): UsersDao
}