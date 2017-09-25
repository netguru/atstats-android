package co.netguru.android.atstats.data.room

import android.arch.persistence.room.Room
import android.content.Context
import co.netguru.android.atstats.data.channels.ChannelsDao
import co.netguru.android.atstats.data.direct.DirectChannelsDao
import co.netguru.android.atstats.data.team.TeamDao
import co.netguru.android.atstats.data.user.UsersDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AtStatsDatabase = Room.databaseBuilder(context, AtStatsDatabase::class.java, AtStatsDatabase.DB_NAME).build()

    @Singleton
    @Provides
    fun provideChannelsDao(atStatsDatabase: AtStatsDatabase): ChannelsDao = atStatsDatabase.channelsDao()

    @Singleton
    @Provides
    fun provideDirectChannelsDao(atStatsDatabase: AtStatsDatabase): DirectChannelsDao = atStatsDatabase.directChannelsDao()

    @Singleton
    @Provides
    fun provideTeamDao(atStatsDatabase: AtStatsDatabase): TeamDao = atStatsDatabase.teamDao()

    @Singleton
    @Provides
    fun provideUsersDao(atStatsDatabase: AtStatsDatabase): UsersDao = atStatsDatabase.usersDao()
}