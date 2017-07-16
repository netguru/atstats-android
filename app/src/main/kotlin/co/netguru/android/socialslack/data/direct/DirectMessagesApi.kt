package co.netguru.android.socialslack.data.direct

import co.netguru.android.socialslack.data.direct.model.DirectChannelResponse
import co.netguru.android.socialslack.data.direct.model.MessageResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectMessagesApi {

    @GET("/api/im.list")
    fun getDirectMessagesList(): Flowable<DirectChannelResponse>

    @GET("/api/im.history")
    fun getDirectMessagesWithUser(
            @Query("channel") channel: String,
            @Query("count") count: Int,
            @Query("latest") latest: String,
            @Query("inclusive") inclusive: Int): Flowable<MessageResponse>

    @GET("/api/im.history")
    fun getDirectMessagesWithUser(
            @Query("channel") channel: String,
            @Query("count") count: Int): Flowable<MessageResponse>
}
