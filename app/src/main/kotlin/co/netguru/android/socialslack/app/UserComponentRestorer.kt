package co.netguru.android.socialslack.app

import android.content.Context
import co.netguru.android.socialslack.data.session.UserSessionRepository
import javax.inject.Inject


class UserComponentRestorer @Inject constructor(private val userSessionRepository: UserSessionRepository,
                                                private val context: Context) {

    fun restoreUserComponent() {
        App.initUserComponent(context, userSessionRepository.getUserSession().userId)
    }
}