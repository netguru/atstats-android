package co.netguru.android.socialslack.data.user

import co.netguru.android.socialslack.data.user.model.UserProfile
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class UsersController @Inject constructor(val usersApi: UsersApi) {

    internal fun getUserProfile(userId: String): Single<UserProfile> {
        return usersApi.getUserProfile(userId)
                .map { it.userProfile }
    }
}