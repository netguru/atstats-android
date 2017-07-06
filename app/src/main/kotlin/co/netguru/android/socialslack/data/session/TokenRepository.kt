package co.netguru.android.socialslack.data.session

import android.content.SharedPreferences
import co.netguru.android.socialslack.app.LocalRepositoryModule
import co.netguru.android.socialslack.data.session.model.Token
import co.netguru.android.socialslack.edit
import rx.Completable
import rx.Single
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(@Named(LocalRepositoryModule.TOKEN_SHARED_PREFERENCES_NAME)
                                          val sharedPreferences: SharedPreferences) {

    fun saveToken(token: Token): Completable {
        return Completable.fromAction({
            sharedPreferences.edit {
                putString(TOKEN_ACCESS_KEY, token.accessToken)
                putString(TOKEN_SCOPE, token.scope)
                putString(TOKEN_TEAM_ID, token.teamId)
            }
        })
    }

    fun getToken(): Single<Token> = Single.just(Token(
            sharedPreferences.getString(TOKEN_ACCESS_KEY, ""),
            sharedPreferences.getString(TOKEN_SCOPE, ""),
            sharedPreferences.getString(TOKEN_TEAM_ID, "")))

    companion object {
        private const val TOKEN_ACCESS_KEY = "token_access"
        private const val TOKEN_SCOPE = "token_scope"
        private const val TOKEN_TEAM_ID = "token_team_id"
    }

}