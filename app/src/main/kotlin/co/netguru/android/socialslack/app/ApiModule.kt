package co.netguru.android.socialslack.app

import co.netguru.android.socialslack.data.channels.ChannelsApi
import co.netguru.android.socialslack.data.direct.DirectMessagesApi
import co.netguru.android.socialslack.data.session.LoginApi
import co.netguru.android.socialslack.data.user.UsersApi
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
    fun provideDirectMessagesApi(retrofit: Retrofit): DirectMessagesApi = retrofit.create(DirectMessagesApi::class.java)

    @Singleton
    @Provides
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)
}