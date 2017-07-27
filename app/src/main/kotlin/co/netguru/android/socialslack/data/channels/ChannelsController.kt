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

    companion object {
        private const val FILE_PARAMETER_NAME = "file"
        private const val FILE_NAME = "channel_statistics.jpg"
        private const val MEDIA_TYPE = "image/jpeg"
    }

    fun getChannelsList(): Single<List<Channel>> = channelsApi.getChannelsList()
            .map { it.channelList }

    fun uploadFileToChannel(channelName: String, fileByteArray: ByteArray): Completable
            = channelsApi.uploadFileToChannel(channelName, createMultipartBody(fileByteArray))
            .flatMapCompletable(this::parseResponse)

    private fun createMultipartBody(fileByteArray: ByteArray): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse(MEDIA_TYPE), fileByteArray)
        return MultipartBody.Part
                .createFormData(FILE_PARAMETER_NAME, FILE_NAME, requestFile)
    }

    private fun parseResponse(fileUploadResponse: FileUploadResponse): Completable {
        with(fileUploadResponse) {
            return if (isSuccessful) Completable.complete() else Completable.error(Throwable(error))
        }
    }
}