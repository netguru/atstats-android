package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelMessage
import co.netguru.android.socialslack.data.channels.model.FileUploadResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelsProviderImpl @Inject constructor(private val channelsApi: ChannelsApi, private val channelsDao: ChannelsDao) : ChannelsProvider {

    // TODO 27.07.2017 REMOVE THIS MOCK
    companion object {
        val MOCK_COUNT = 1000
        private const val FILE_PARAMETER_NAME = "file"
        private const val FILE_NAME = "channel_statistics.jpg"
        private const val MEDIA_TYPE = "image/jpeg"
    }

    // TODO 27.07.2017 this should be get from the database
    override fun getMessagesForChannel(channelId: String):
            Observable<ChannelMessage> = channelsApi
            .getChannelsHistory(channelId, MOCK_COUNT, null, null, null, null)
            .flatMapObservable { it -> Observable.fromIterable(it.messageList) }

    override fun getChannelsList(): Single<List<Channel>> = channelsApi.getChannelsList()
            .map { it.channelList }

    override fun uploadFileToChannel(channelName: String, fileByteArray: ByteArray): Completable {
        return channelsApi.uploadFileToChannel(channelName, createMultipartBody(fileByteArray))
                .flatMapCompletable(this::parseResponse)
    }

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