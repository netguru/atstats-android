package co.netguru.android.socialslack.app

import co.netguru.android.socialslack.data.session.RequestInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@Module
class NetworkModule {

    companion object API {
        const val URI_SCHEME = "https"
        const val URI_AUTHORITY = "slack.com"
        const val SLACK_BASE_URL = "https://slack.com/"
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(API.SLACK_BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideOkHttp(requestInterceptor: RequestInterceptor) = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(requestInterceptor)
            .build()
}