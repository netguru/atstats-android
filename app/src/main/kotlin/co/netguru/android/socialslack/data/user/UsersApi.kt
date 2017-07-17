package co.netguru.android.socialslack.data.user

import co.netguru.android.socialslack.data.user.model.UserProfileResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("api/users.profile.get")
    fun getUserProfile(@Query("user") userId: String): Single<UserProfileResponse>
}