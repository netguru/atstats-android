package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelMessage
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.channels.model.FileUploadResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelsController @Inject constructor(private val channelsApi: ChannelsApi,
                                             private val channelsDao: ChannelsDao) {

    companion object {
        private const val COUNT = 1000
        private const val FILE_PARAMETER_NAME = "file"
        private const val FILE_NAME = "channel_statistics.jpg"
        private const val MEDIA_TYPE = "image/jpeg"
        private const val SINCE_TIME: Long = 60 * 60 * 24 * 30 // 30 days in seconds
        private val currentTime = System.currentTimeMillis() / 1000
    }

    fun getChannelsList(): Single<List<Channel>> = channelsApi.getChannelsList()
            .subscribeOn(Schedulers.io())
            .map { it.channelList }

    fun countChannelStatistics(channelId: String, channelName: String, user: String) =
            getMessagesAllMessagesFromApi(channelId)
                    .observeOn(Schedulers.computation())
                    .flattenAsObservable { it }
                    .collect({ ChannelCount(user) }, { t1: ChannelCount?, t2: ChannelMessage? -> t1?.accept(t2) })
                    .map { ChannelStatistics(channelId, channelName, it.totalMessageCount, it.hereCount, it.mentionsCount, it.myMessageCount) }
                    .observeOn(Schedulers.io())
                    .doAfterSuccess { channelsDao.insertChannel(it) }

    fun uploadFileToChannel(channelName: String, fileByteArray: ByteArray): Completable {
        return channelsApi.uploadFileToChannel(channelName, createMultipartBody(fileByteArray))
                .flatMapCompletable(this::parseResponse)
    }

    private fun getMessagesAllMessagesFromApi(channelId: String) =
            getMessagesFromApi(channelId, (currentTime - SINCE_TIME).toString())
                    .subscribeOn(Schedulers.io())

    private fun getMessagesFromApi(channelId: String, sinceTime: String): Single<List<ChannelMessage>> {
        var lastTimestamp: String? = (currentTime).toString()

        return Flowable.range(0, Int.MAX_VALUE)
                .concatMap {
                    getHistoryMessagesOnIOFromAPI(channelId, lastTimestamp, sinceTime)
                            .toFlowable()
                }
                .doOnNext { if (it.messageList.isNotEmpty()) lastTimestamp = it.messageList.last().timeStamp }
                .takeUntil { !it.hasMore }
                .map { it.messageList }
                .scan(this::addLists)
                .lastOrError()
    }

    private fun addLists(a: List<ChannelMessage>, b: List<ChannelMessage>): List<ChannelMessage> {
        val list: MutableList<ChannelMessage> = mutableListOf()
        list.addAll(a)
        list.addAll(b)
        return list.toList()
    }

    private fun getHistoryMessagesOnIOFromAPI(channelId: String, latestTimestamp: String?, oldestTimestamp: String?) =
            channelsApi.getChannelsHistory(channelId, COUNT, latestTimestamp, oldestTimestamp)
                    .subscribeOn(Schedulers.io())

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

    data class ChannelCount(val currentUser: String, var hereCount: Int = 0, var mentionsCount: Int = 0, var myMessageCount: Int = 0, var totalMessageCount: Int = 0) {

        fun accept(channelMessage: ChannelMessage?): ChannelCount {
            channelMessage?.let {
                totalMessageCount++
                channelMessage.text?.apply {
                    if (contains(ChannelMessage.HERE_TAG)) hereCount++
                    if (contains(String.format(ChannelMessage.USER_MENTION, currentUser))) mentionsCount++
                }
                if (channelMessage.user?.contains(currentUser) ?: false) myMessageCount++
            }
            return this
        }
    }
}