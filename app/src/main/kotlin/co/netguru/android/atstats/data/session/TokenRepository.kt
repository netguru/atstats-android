package co.netguru.android.atstats.data.session

import android.content.SharedPreferences
import co.netguru.android.atstats.app.SessionRepositoryModule
import co.netguru.android.atstats.common.extensions.edit
import co.netguru.android.atstats.data.session.model.Token
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TokenRepository @Inject constructor(@Named(SessionRepositoryModule.TOKEN_SHARED_PREFERENCES_NAME)
                                          private val sharedPreferences: SharedPreferences) {

    companion object {
        const val EMPTY_TOKEN = ""
        private const val TOKEN_ACCESS_KEY = "token_access"
        private const val TOKEN_SCOPE = "token_scope"
    }

    fun saveToken(token: Token): Completable {
        return Completable.fromAction({
            sharedPreferences.edit {
                putString(TOKEN_ACCESS_KEY, token.accessToken)
                putString(TOKEN_SCOPE, token.scope)
            }
        })
    }

    fun getToken(): Token = Token(sharedPreferences.getString(TOKEN_ACCESS_KEY, EMPTY_TOKEN),
            sharedPreferences.getString(TOKEN_SCOPE, EMPTY_TOKEN))

    fun removeToken(): Completable = Completable.fromAction({
        sharedPreferences.edit {
            clear()
        }
    })
}