package co.netguru.android.socialslack.app

import android.content.Context
import co.netguru.android.socialslack.data.cache.CacheRequestInterceptor
import co.netguru.android.socialslack.data.cache.CacheResponseInterceptor
import co.netguru.android.socialslack.data.session.RequestInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


@Module
class NetworkModule {

    companion object API {
        const val URI_SCHEME = "https"
        const val URI_AUTHORITY = "slack.com"
        const val SLACK_BASE_URL = "https://slack.com/"
        const val HTTP_CACHE_SIZE: Long = 1024 * 1024 * 20
        const val HTTP_CACHE_DIRECTORY = "cache"
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
    fun provideOkHttp(requestInterceptor: RequestInterceptor, cache: Cache,
                      cacheRequestInterceptor: CacheRequestInterceptor,
                      cacheResponseInterceptor: CacheResponseInterceptor) = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addInterceptor(requestInterceptor)
            .addInterceptor(cacheRequestInterceptor)
            .addNetworkInterceptor(cacheResponseInterceptor)
            .cache(cache)
            .build()

    @Provides
    @Singleton
    fun provideHttpCache(context: Context): Cache {
        val cacheDirectory = File(context.getCacheDir(), HTTP_CACHE_DIRECTORY)
        return Cache(cacheDirectory, HTTP_CACHE_SIZE)
    }

    @Provides
    @Singleton
    fun provideCacheRequestInterceptor(): CacheRequestInterceptor {
        return CacheRequestInterceptor()
    }

    @Provides
    @Singleton
    fun provideCacheResponseInterceptor(): CacheResponseInterceptor {
        return CacheResponseInterceptor()
    }
}