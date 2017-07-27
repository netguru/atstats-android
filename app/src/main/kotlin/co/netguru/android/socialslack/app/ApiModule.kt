package co.netguru.android.socialslack.app

import co.netguru.android.socialslack.data.channels.ChannelsProvider
import co.netguru.android.socialslack.data.channels.ChannelsProviderImpl
import co.netguru.android.socialslack.data.channels.ChannelsApi
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
    fun provideChannelHistoryProvider(channelsApi: ChannelsApi): ChannelsProvider = ChannelsProviderImpl(channelsApi)
}