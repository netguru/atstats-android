package co.netguru.android.socialslack.data.session

import rx.Single
import javax.inject.Inject
import android.net.Uri
import co.netguru.android.socialslack.BuildConfig
import co.netguru.android.socialslack.app.NetworkModule


class TokenController @Inject constructor() {

    companion object {
        private val OAUTH_AUTHORIZE_ENDPOINT = "oauth/authorize"
        private val CLIENT_ID_KEY = "client_id"
        private val SCOPE = "scope"
    }

    fun getOauthAuthorizeUri(): Single<String> = Single.just(getAuthorizeUri())

    private fun getAuthorizeUri(): String = Uri.Builder()
            .scheme(NetworkModule.API.URI_SCHEME)
            .authority(NetworkModule.API.URI_AUTHORITY)
            .appendEncodedPath(OAUTH_AUTHORIZE_ENDPOINT)
            .appendQueryParameter(CLIENT_ID_KEY, BuildConfig.SLACK_CLIENT_ID)
            .appendQueryParameter(SCOPE, SlackApiScope.getSlackApiScope())
            .build()
            .toString()

}