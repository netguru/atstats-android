package co.netguru.android.atstats.data.session

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestInterceptor @Inject constructor(val tokenRepository: TokenRepository) : Interceptor {

    companion object {
        private const val TOKEN_QUERY_PARAM = "token"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = tokenRepository.getToken().accessToken

        if (token == TokenRepository.EMPTY_TOKEN) {
            return chain.proceed(originalRequest)
        } else {
            return chain.proceed(getRequestWithToken(originalRequest, token))
        }
    }

    private fun getRequestWithToken(originalRequest: Request, token: String): Request {
        val urlWithToken = originalRequest.url().newBuilder()
                .addQueryParameter(TOKEN_QUERY_PARAM, token)
                .build()

        return originalRequest.newBuilder()
                .url(urlWithToken)
                .build()
    }
}