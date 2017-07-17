package co.netguru.android.socialslack.data.cache

import io.fabric.sdk.android.services.network.HttpRequest.HEADER_CACHE_CONTROL
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class CacheRequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val cacheControl = chain.request().header(AppCacheStrategy.HEADER_CACHE_CONTROL)
        val request = generateCacheRequest(chain.request(), cacheControl)
        return chain.proceed(request)
    }

    private fun generateCacheRequest(originalRequest: Request, cacheControl: String?): Request {
        if (cacheControl != null) {
            return originalRequest
        } else {
            return getNoCacheRequest(originalRequest)
        }
    }

    private fun getNoCacheRequest(originalRequest: Request): Request {
        return originalRequest.newBuilder()
                .header(HEADER_CACHE_CONTROL, AppCacheStrategy.CACHE_CONTROL_NO_CACHE)
                .build()
    }
}
