package co.netguru.android.socialslack.data.user

import co.netguru.android.socialslack.app.scope.UserScope
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.user.model.User
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.data.user.model.UserStatistic.Companion.toStatisticsView
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@UserScope
class UsersController @Inject constructor(private val usersApi: UsersApi,
                                          private val directChannelsDao: DirectChannelsDao) {

    internal fun getUserInfo(userId: String): Single<User> {
        return usersApi.getUserInfo(userId)
                .map { it.user }
    }

    fun getAllDirectChannelsStatistics() = directChannelsDao.getAllDirectChannels()

    fun getAllUsersInfo(statisticsList: List<DirectChannelStatistics>): Single<List<UserStatistic>> =
            Flowable.fromIterable(statisticsList)
                    .flatMap { (_, userId, messagesFromUs, messagesFromOtherUser) ->
                        getUserInfo(userId).toFlowable()
                                .map {
                                    it.toStatisticsView(
                                            messagesFromUs,
                                            messagesFromOtherUser
                                    )
                                }
                                // Need it to make it concurrent
                                .subscribeOn(Schedulers.io())
                    }
                    .toList()
                    .cache()
}