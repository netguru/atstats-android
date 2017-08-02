package co.netguru.android.socialslack.data.user.profile

import co.netguru.android.socialslack.data.user.UsersApi
import co.netguru.android.socialslack.data.user.model.UserStatistic
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersProfileController @Inject constructor(private val usersApi: UsersApi) {

    fun getUserWithPresence(userStatistic: UserStatistic): Flowable<UserStatistic> {
        return usersApi.getUserPresence(userStatistic.id)
                .doOnSuccess { userStatistic.presence = Presence.getEnumForValue(it.presence) }
                .map { userStatistic }
                .toFlowable()
                .subscribeOn(Schedulers.io())
    }
}