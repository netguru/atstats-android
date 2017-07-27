package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.ChannelList

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

internal interface ChannelsApi {

    @GET("/api/channels.list")
    fun getChannelsList(): Single<ChannelList>

    @Multipart
    @POST("api/files.upload")
    fun uploadFileToChannel(@Query("channels") channel: String,
                            @Part file: MultipartBody.Part): Completable
}
