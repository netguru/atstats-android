package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.ChannelList
import io.reactivex.Single
import retrofit2.http.*

interface ChannelsApi {

    @GET("/api/channels.list")
    fun getChannelsList(@Query("token") token : String): Single<ChannelList>
}
