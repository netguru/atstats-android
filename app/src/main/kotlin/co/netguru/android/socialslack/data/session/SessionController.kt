package co.netguru.android.socialslack.data.session

import javax.inject.Inject
import android.net.Uri
import co.netguru.android.socialslack.BuildConfig
import co.netguru.android.socialslack.app.NetworkModule
import co.netguru.android.socialslack.data.session.model.SlackApiScope
import co.netguru.android.socialslack.data.session.model.Token
import co.netguru.android.socialslack.data.session.model.TokenCheck
import co.netguru.android.socialslack.data.session.model.UserSession
import io.reactivex.Completable
import io.reactivex.Single

import timber.log.Timber
import javax.inject.Singleton

@Singleton
class SessionController @Inject constructor(private val loginApi: LoginApi,
                                            private val tokenRepository: TokenRepository,
                                            private val userSessionRepository: UserSessionRepository) {

    companion object {
        private val OAUTH_AUTHORIZE_ENDPOINT = "oauth/authorize"
        private val CLIENT_ID_KEY = "client_id"
        private val SCOPE = "scope"
    }

    fun getOauthAuthorizeUri(): Single<Uri> = Single.just(getAuthorizeUri())

    fun requestNewToken(code: String): Single<Token> =
            loginApi.requestToken(BuildConfig.SLACK_CLIENT_ID, BuildConfig.SLACK_CLIENT_SECRET, code)

    fun checkToken(): Single<TokenCheck> = loginApi.checkToken()
            .flatMap { token ->
                saveUserSession(UserSession(token.userId, token.teamId))
                        .toSingle { token }
            }

    fun isTokenValid(): Single<TokenCheck> {
        return Single.just(tokenRepository.getToken())
                .flatMap {
                    if (it.accessToken == TokenRepository.EMPTY_TOKEN) {
                        Single.just(TokenCheck(false, UserSessionRepository.EMPTY_FIELD, UserSessionRepository.EMPTY_FIELD))
                    } else {
                        checkToken()
                    }
                }
    }

    fun saveToken(token: Token): Completable = tokenRepository.saveToken(token)
            .doOnComplete({ Timber.d("Token saved in repository") })

    fun saveUserSession(userSession: UserSession): Completable =
            userSessionRepository.saveUserSession(userSession)
                    .doOnComplete({ Timber.d("Token details saved in repository") })

    fun getToken(): Single<Token> = Single.just(tokenRepository.getToken())

    fun getUserSession(): Single<UserSession> = Single.just(userSessionRepository.getUserSession())

    fun removeToken(): Completable {
        TODO("Clear SharedPreferences and revoke token")
    }

    private fun getAuthorizeUri(): Uri = Uri.Builder()
            .scheme(NetworkModule.API.URI_SCHEME)
            .authority(NetworkModule.API.URI_AUTHORITY)
            .appendEncodedPath(OAUTH_AUTHORIZE_ENDPOINT)
            .appendQueryParameter(CLIENT_ID_KEY, BuildConfig.SLACK_CLIENT_ID)
            .appendQueryParameter(SCOPE, SlackApiScope.getSlackApiScope())
            .build()
}