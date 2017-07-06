package co.netguru.android.socialslack.data.session

import javax.inject.Inject
import android.net.Uri
import co.netguru.android.socialslack.BuildConfig
import co.netguru.android.socialslack.app.NetworkModule
import co.netguru.android.socialslack.data.session.model.SlackApiScope
import co.netguru.android.socialslack.data.session.model.Token
import io.reactivex.Completable
import io.reactivex.Single

import timber.log.Timber


class TokenController @Inject constructor(val loginApi: LoginApi, val tokenRepository: TokenRepository) {

    fun getOauthAuthorizeUri(): Single<Uri> = Single.just(getAuthorizeUri())

    fun requestNewToken(code: String): Single<Token> =
            loginApi.requestToken(BuildConfig.SLACK_CLIENT_ID, BuildConfig.SLACK_CLIENT_SECRET, code)

    fun isTokenValid(): Single<Boolean> {
        TODO("Check with api and sharedpreferences")
    }

    fun saveToken(token: Token): Completable = tokenRepository.saveToken(token)
            .doOnComplete({ Timber.d("Token saved in repository") })

    fun getToken(): Single<Token> = tokenRepository.getToken()

    fun removeToken(): Completable {
        TODO("Clear sharedpreferences and revoke token")
    }

    private fun getAuthorizeUri(): Uri = Uri.Builder()
            .scheme(NetworkModule.API.URI_SCHEME)
            .authority(NetworkModule.API.URI_AUTHORITY)
            .appendEncodedPath(OAUTH_AUTHORIZE_ENDPOINT)
            .appendQueryParameter(CLIENT_ID_KEY, BuildConfig.SLACK_CLIENT_ID)
            .appendQueryParameter(SCOPE, SlackApiScope.getSlackApiScope())
            .build()

    companion object {
        private val OAUTH_AUTHORIZE_ENDPOINT = "oauth/authorize"
        private val CLIENT_ID_KEY = "client_id"
        private val SCOPE = "scope"
    }
}