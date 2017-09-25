package co.netguru.android.atstats.app

import co.netguru.android.atstats.data.channels.ChannelsApi
import co.netguru.android.atstats.data.direct.DirectChannnelsApi
import co.netguru.android.atstats.data.session.LoginApi
import co.netguru.android.atstats.data.team.TeamApi
import co.netguru.android.atstats.data.user.UsersApi
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
    fun provideDirectMessagesApi(retrofit: Retrofit): DirectChannnelsApi = retrofit.create(DirectChannnelsApi::class.java)

    @Singleton
    @Provides
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)

    @Singleton
    @Provides
    fun provideTeamApi(retrofit: Retrofit): TeamApi = retrofit.create(TeamApi::class.java)
}