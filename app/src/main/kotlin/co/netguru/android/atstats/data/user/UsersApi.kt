package co.netguru.android.atstats.data.user

import co.netguru.android.atstats.data.user.model.UserList
import co.netguru.android.atstats.data.user.model.UserResponse
import co.netguru.android.atstats.data.user.profile.model.UserPresence
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("api/users.info")
    fun getUserInfo(@Query("user") userId: String): Single<UserResponse>

    @GET("api/users.getPresence")
    fun getUserPresence(@Query("user") userId: String): Single<UserPresence>

    @GET("api/users.list")
    fun getUserList(): Single<UserList>
}