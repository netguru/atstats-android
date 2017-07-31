package co.netguru.android.socialslack.app

import android.app.Application
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import co.netguru.android.socialslack.data.channels.ChannelsProvider
import co.netguru.android.socialslack.data.channels.ChannelsProviderImpl
import co.netguru.android.socialslack.data.channels.ChannelsApi
import co.netguru.android.socialslack.data.room.SlackSocialDatabase
import co.netguru.android.socialslack.data.session.LoginApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi = retrofit.create(LoginApi::class.java)

    @Singleton
    @Provides
    fun provideChannelsApi(retrofit: Retrofit): ChannelsApi = retrofit.create(ChannelsApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(context: Application): SlackSocialDatabase = Room.databaseBuilder(context, SlackSocialDatabase::class.java, SlackSocialDatabase.DB_NAME).build()

    @Singleton
    @Provides
    fun provideChannelHistoryProvider(channelsApi: ChannelsApi): ChannelsProvider = ChannelsProviderImpl(channelsApi)
}