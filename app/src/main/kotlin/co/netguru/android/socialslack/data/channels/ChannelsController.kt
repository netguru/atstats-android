package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.FileUploadResponse
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.RequestBody

@Singleton
class ChannelsController @Inject constructor(private val channelsApi: ChannelsApi) {

    fun getChannelsList(): Single<List<Channel>> = channelsApi.getChannelsList()
            .map { it.channelList }

    fun uploadFileToChannel(channelName: String, fileByteArray: ByteArray): Completable
            = channelsApi.uploadFileToChannel(channelName, createMultipartBody(fileByteArray))
            .flatMapCompletable(this::parseResponse)

    private fun createMultipartBody(fileByteArray: ByteArray): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), fileByteArray)
        return MultipartBody.Part
                .createFormData("file", "channel_statistics.jpg", requestFile)
    }

    private fun parseResponse(fileUploadResponse: FileUploadResponse): Completable {
        with(fileUploadResponse) {
            return if (isSuccessful) Completable.complete() else Completable.error(Throwable(error))
        }
    }
}