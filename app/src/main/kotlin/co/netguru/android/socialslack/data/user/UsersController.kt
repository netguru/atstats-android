package co.netguru.android.socialslack.data.user

import co.netguru.android.socialslack.app.scope.UserScope
import co.netguru.android.socialslack.data.user.model.User
import io.reactivex.Single
import javax.inject.Inject

@UserScope
class UsersController @Inject constructor(private val usersApi: UsersApi) {

    internal fun getUserInfo(userId: String): Single<User> {
        return usersApi.getUserInfo(userId)
                .map { it.user }
    }
}