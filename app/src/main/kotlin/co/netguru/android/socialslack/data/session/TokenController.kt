package co.netguru.android.socialslack.data.session

import rx.Single
import javax.inject.Inject
import android.net.Uri
import co.netguru.android.socialslack.BuildConfig
import co.netguru.android.socialslack.app.NetworkModule
import co.netguru.android.socialslack.data.session.model.SlackApiScope
import co.netguru.android.socialslack.data.session.model.Token
import rx.Completable


class TokenController @Inject constructor(val loginApi: LoginApi, val tokenRepository: TokenRepository) {

    fun getOauthAuthorizeUri(): Single<String> = Single.just(getAuthorizeUri())

    fun requestNewToken(clientId: String, clientSecret: String, code: String): Single<Token> =
            loginApi.requestToken(clientId, clientSecret, code)

    fun isTokenValid(): Single<Boolean> {
        TODO("Check with api and sharedpreferences")
    }

    fun saveToken(token: Token): Completable = tokenRepository.saveToken(token)

    fun removeToken(): Completable {
        TODO("Clear sharedpreferences and revoke token")
    }

    private fun getAuthorizeUri(): String = Uri.Builder()
            .scheme(NetworkModule.API.URI_SCHEME)
            .authority(NetworkModule.API.URI_AUTHORITY)
            .appendEncodedPath(OAUTH_AUTHORIZE_ENDPOINT)
            .appendQueryParameter(CLIENT_ID_KEY, BuildConfig.SLACK_CLIENT_ID)
            .appendQueryParameter(SCOPE, SlackApiScope.getSlackApiScope())
            .build()
            .toString()

    companion object {
        private val OAUTH_AUTHORIZE_ENDPOINT = "oauth/authorize"
        private val CLIENT_ID_KEY = "client_id"
        private val SCOPE = "scope"
    }

}