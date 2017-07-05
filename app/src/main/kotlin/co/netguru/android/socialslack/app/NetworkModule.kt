package co.netguru.android.socialslack.app

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor


@Module
class NetworkModule {

    companion object {
        val SLACK_BASE_URL = "https://slack.com/api"
    }

    @Singleton
    @Provides
    fun provideRetorfit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .baseUrl(SLACK_BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideGson() = GsonBuilder()
            .create()

    @Singleton
    @Provides
    fun provideOkHttp() = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
}