package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.ChannelHistory
import co.netguru.android.socialslack.data.channels.model.ChannelList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ChannelsApi {

    @GET("/api/channels.list")
    fun getChannelsList(): Single<ChannelList>

    @GET("/api/channels.history")
    fun getChannelsHistory(@Query("channel") channelId: String, @Query("count") count: Int?,
                           @Query("inclusive") inclusive: Boolean?, @Query("latest") latest: Long?,
                           @Query("oldest") oldest: Long?, @Query("unread") unRead: Boolean?): Single<ChannelHistory>
}