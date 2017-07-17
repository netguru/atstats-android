package co.netguru.android.socialslack.data.cache

import io.fabric.sdk.android.services.network.HttpRequest.HEADER_CACHE_CONTROL
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Singleton

@Singleton
class CacheResponseInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val cacheStrategy = chain.request().header(AppCacheStrategy.HEADER_CACHE_CONTROL)

        val originalResponse = chain.proceed(chain.request())
        return generateCacheResponse(originalResponse, cacheStrategy)
    }

    private fun generateCacheResponse(originalResponse: Response, cacheStrategy: String?): Response {
        if (cacheStrategy == null) {
            return originalResponse.newBuilder()
                    .header(HEADER_CACHE_CONTROL, AppCacheStrategy.CACHE_CONTROL_NO_CACHE)
                    .build()
        } else {
            return originalResponse.newBuilder()
                    .header(HEADER_CACHE_CONTROL, AppCacheStrategy.CACHE_CONTROL_MAX_AGE +
                            AppCacheStrategy.CACHE_TIME_SECONDS)
                    .build()
        }
    }
}
