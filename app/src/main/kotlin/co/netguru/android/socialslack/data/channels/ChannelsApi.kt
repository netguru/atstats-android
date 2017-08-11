package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.ChannelHistory
import co.netguru.android.socialslack.data.channels.model.ChannelList
import co.netguru.android.socialslack.data.channels.model.FileUploadResponse

import io.reactivex.Single

import okhttp3.MultipartBody
import retrofit2.http.*

interface ChannelsApi {

    @GET("/api/channels.list")
    fun getChannelsList(): Single<ChannelList>

    @Multipart
    @POST("api/files.upload")
    fun uploadFileToChannel(@Query("channels") channel: String,
                            @Part file: MultipartBody.Part): Single<FileUploadResponse>

    @GET("/api/channels.history")
    fun getChannelsHistory(@Query("channel") channelId: String, @Query("count") count: Int?,
                           @Query("latest") latest: String?,
                           @Query("oldest") oldest: String?): Single<ChannelHistory>
}