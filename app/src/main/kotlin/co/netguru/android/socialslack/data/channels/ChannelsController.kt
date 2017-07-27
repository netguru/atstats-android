package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.RequestBody


@Singleton
internal class ChannelsController @Inject constructor(private val channelsApi: ChannelsApi) {

    internal fun getChannelsList(): Single<List<Channel>> = channelsApi.getChannelsList()
            .map { it.channelList }

    internal fun uploadFileToChannel(channelName: String, fileByteArray: ByteArray): Completable
            = channelsApi.uploadFileToChannel(channelName, createMultipartBody(fileByteArray))

    private fun createMultipartBody(fileByteArray: ByteArray): MultipartBody.Part {
        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), fileByteArray)
        return MultipartBody.Part
                .createFormData("channel_statistics", "channel_statistics.jpg", requestFile)
    }
}