package co.netguru.android.socialslack.data.user

import co.netguru.android.socialslack.data.user.model.User
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UsersController @Inject constructor(val usersApi: UsersApi) {

    internal fun getUserInfo(userId: String): Single<User> {
        return usersApi.getUserInfo(userId)
                .map { it.user }
    }
}