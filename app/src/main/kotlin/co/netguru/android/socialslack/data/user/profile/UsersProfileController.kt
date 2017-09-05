package co.netguru.android.socialslack.data.user.profile

import co.netguru.android.socialslack.data.user.UsersApi
import co.netguru.android.socialslack.data.user.model.UserDB
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.data.user.profile.model.UserWithPresence
import io.reactivex.Flowable
import io.reactivex.Single

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersProfileController @Inject constructor(private val usersApi: UsersApi) {

    fun getUserWithPresence(userStatistic: UserStatistic): Flowable<UserStatistic> {
        return usersApi.getUserPresence(userStatistic.id)
                .doOnSuccess { userStatistic.presence = it.presence }
                .map { userStatistic }
                .toFlowable()
    }

    fun getUserWithPresence(user: UserDB): Single<UserWithPresence> =
            usersApi.getUserPresence(user.id)
                    .map { UserWithPresence.createUserDBWithPresence(user, it.presence) }
}