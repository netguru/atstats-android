package co.netguru.android.socialslack.data.user

import co.netguru.android.socialslack.app.scope.UserScope
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.user.model.User
import co.netguru.android.socialslack.data.user.model.UserDB
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.data.user.model.UserStatistic.Companion.toStatisticsView
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@UserScope
class UsersController @Inject constructor(private val usersApi: UsersApi,
                                          private val usersDao: UsersDao,
                                          private val directChannelsDao: DirectChannelsDao) {
    // TODO 23.08.2017 to be replace and use getUserAndStore
    internal fun getUserInfo(userId: String): Single<User> {
        return usersApi.getUserInfo(userId)
                .map { it.user }
    }

    fun getUsersList(): Single<List<User>> = usersApi.getUserList()
            .map { it.usersList }

    fun getAllDirectChannelsStatistics() = directChannelsDao.getAllDirectChannels()

    fun getAllUsersInfo(statisticsList: List<DirectChannelStatistics>): Single<List<UserStatistic>> =
            Flowable.fromIterable(statisticsList)
                    .flatMap { (_, userId, messagesFromUs, messagesFromOtherUser, streakDays, isCurrentUser) ->
                        getUserInfo(userId).toFlowable()
                                .map {
                                    it.toStatisticsView(
                                            messagesFromUs,
                                            messagesFromOtherUser,
                                            isCurrentUser,
                                            streakDays
                                    )
                                }
                                // Need it to make it concurrent
                                .subscribeOn(Schedulers.io())
                    }
                    .toList()
                    .cache()

    fun getUserAndStore(userId: String): Single<User> = getUserInfo(userId)
            .doAfterSuccess { usersDao.insertUser(UserDB.createUserDB(it)) }
}