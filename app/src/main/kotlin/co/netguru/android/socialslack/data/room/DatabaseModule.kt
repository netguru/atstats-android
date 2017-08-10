package co.netguru.android.socialslack.data.room

import android.arch.persistence.room.Room
import android.content.Context
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): SlackSocialDatabase = Room.databaseBuilder(context, SlackSocialDatabase::class.java, SlackSocialDatabase.DB_NAME).build()

    @Singleton
    @Provides
    fun provideChannelsDao(slackSocialDatabase: SlackSocialDatabase): ChannelsDao = slackSocialDatabase.channelsDao()

    @Singleton
    @Provides
    fun provideDirectChannelsDao(slackSocialDatabase: SlackSocialDatabase): DirectChannelsDao = slackSocialDatabase.directChannelsDao()
}