package co.netguru.android.socialslack.data.direct

import co.netguru.android.socialslack.data.direct.model.DirectChannelList
import co.netguru.android.socialslack.data.direct.model.DirectChannelsHistory
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectChannnelsApi {

    @GET("/api/im.list")
    fun getDirectMessagesList(): Single<DirectChannelList>

    @GET("/api/im.history")
    fun getDirectMessagesWithUser(
            @Query("channel") channel: String,
            @Query("count") count: Int,
            @Query("latest") latest: String,
            @Query("oldest") oldest: String): Single<DirectChannelsHistory>
}