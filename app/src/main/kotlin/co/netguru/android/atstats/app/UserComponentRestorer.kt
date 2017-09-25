package co.netguru.android.atstats.app

import android.content.Context
import co.netguru.android.atstats.data.session.UserSessionRepository
import javax.inject.Inject


class UserComponentRestorer @Inject constructor(private val userSessionRepository: UserSessionRepository,
                                                private val context: Context) {

    fun restoreUserComponent() {
        App.initUserComponent(context, userSessionRepository.getUserSession().userId)
    }
}