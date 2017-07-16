package co.netguru.android.socialslack.data.user

import co.netguru.android.socialslack.data.user.model.User
import co.netguru.android.socialslack.data.user.model.UserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("api/users.info")
    fun getUserInfo(@Query("user") userId: String): Single<UserResponse>
}