package co.netguru.android.atstats.data.session

import android.content.SharedPreferences
import co.netguru.android.atstats.app.SessionRepositoryModule
import co.netguru.android.atstats.common.extensions.edit
import co.netguru.android.atstats.data.session.model.UserSession
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class UserSessionRepository @Inject constructor(@Named(SessionRepositoryModule.USER_SHARED_PREFERENCES_NAME)
                                                private val sharedPreferences: SharedPreferences) {

    companion object {
        const val EMPTY_FIELD = ""
        private const val SESSION_USER_ID = "token_user_id"
        private const val SESSION_TEAM_ID = "token_team_id"
    }

    fun saveUserSession(userSession: UserSession): Completable = Completable.fromAction {
        sharedPreferences.edit {
            putString(SESSION_USER_ID, userSession.userId)
            putString(SESSION_TEAM_ID, userSession.teamId)
        }
    }


    fun getUserSession(): UserSession = UserSession(
            sharedPreferences.getString(SESSION_USER_ID, EMPTY_FIELD),
            sharedPreferences.getString(SESSION_TEAM_ID, EMPTY_FIELD)
    )

    fun removeUserSession(): Completable = Completable.fromAction {
        sharedPreferences.edit {
            clear()
        }
    }
}