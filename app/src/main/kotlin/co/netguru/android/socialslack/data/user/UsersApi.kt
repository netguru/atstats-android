package co.netguru.android.socialslack.data.user

import co.netguru.android.socialslack.data.user.model.UserResponse
import co.netguru.android.socialslack.data.user.profile.UserPresence
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("api/users.info")
    fun getUserInfo(@Query("user") userId: String): Single<UserResponse>

    @GET("api/users.getPresence")
    fun getUserPresence(@Query("user") userId: String):Single<UserPresence>
}