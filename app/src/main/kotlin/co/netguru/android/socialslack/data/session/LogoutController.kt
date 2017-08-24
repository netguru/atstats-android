package co.netguru.android.socialslack.data.session

import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import co.netguru.android.socialslack.data.team.TeamDao
import co.netguru.android.socialslack.data.user.UsersDao
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogoutController @Inject constructor(private val channelsDao: ChannelsDao,
                                           private val directChannelsDao: DirectChannelsDao,
                                           private val teamDao: TeamDao,
                                           private val usersDao: UsersDao,
                                           private val sessionController: SessionController,
                                           private val loginApi: LoginApi) {

    fun logout(): Completable = removeAllData()
            .mergeWith(sessionController.removeSession())
            .mergeWith(loginApi.revokeToken())

    private fun removeAllData(): Completable = Completable.fromAction({
        channelsDao.deleteAllChannels()
        directChannelsDao.deleteAllDirectChannelsStatistics()
        teamDao.deleteAllTeam()
        usersDao.deleteAllUsers()
    })
}