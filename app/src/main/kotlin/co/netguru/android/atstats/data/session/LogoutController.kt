package co.netguru.android.atstats.data.session

import android.content.Context
import co.netguru.android.atstats.app.App
import co.netguru.android.atstats.data.channels.ChannelsDao
import co.netguru.android.atstats.data.direct.DirectChannelsDao
import co.netguru.android.atstats.data.team.TeamDao
import co.netguru.android.atstats.data.theme.ThemeController
import co.netguru.android.atstats.data.theme.ThemeOption
import co.netguru.android.atstats.data.user.UsersDao
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutController @Inject constructor(private val channelsDao: ChannelsDao,
                                           private val directChannelsDao: DirectChannelsDao,
                                           private val teamDao: TeamDao,
                                           private val usersDao: UsersDao,
                                           private val sessionController: SessionController,
                                           private val loginApi: LoginApi,
                                           private val themeController: ThemeController,
                                           private val context: Context) {

    fun logout(): Completable = removeAllData()
            .mergeWith(sessionController.removeSession())
            .mergeWith(loginApi.revokeToken())
            .mergeWith(themeController.saveThemeOption(ThemeOption.COLOURFUL))
            .doOnComplete(this::resetUserComponentAfterLogout)

    private fun removeAllData(): Completable = Completable.fromAction({
        channelsDao.deleteAllChannels()
        directChannelsDao.deleteAllDirectChannelsStatistics()
        teamDao.deleteAllTeam()
        usersDao.deleteAllUsers()
    })

    private fun resetUserComponentAfterLogout() {
        App.releaseUserComponent(context)
    }
}