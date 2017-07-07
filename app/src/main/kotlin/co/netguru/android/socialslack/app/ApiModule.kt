package co.netguru.android.socialslack.app

import co.netguru.android.socialslack.data.session.LoginApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit) : LoginApi = retrofit.create(LoginApi::class.java)
}