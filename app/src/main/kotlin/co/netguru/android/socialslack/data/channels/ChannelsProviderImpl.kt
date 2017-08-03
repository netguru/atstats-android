package co.netguru.android.socialslack.data.channels

import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.model.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChannelsProviderImpl @Inject constructor(private val channelsApi: ChannelsApi, private val channelsDao: ChannelsDao) : ChannelsProvider {

    // TODO 27.07.2017 REMOVE THIS MOCK
    companion object {
        private const val COUNT = 1000
        private const val FILE_PARAMETER_NAME = "file"
        private const val FILE_NAME = "channel_statistics.jpg"
        private const val MEDIA_TYPE = "image/jpeg"
        private const val SINCE_TIME: Long = 60 * 60 * 24 * 30 // 30 days in seconds
        val currentTime = System.currentTimeMillis() / 1000
    }

    // TODO 27.07.2017 this should be get from the database
    override fun getMessagesForChannel(channelId: String):
            Single<List<ChannelMessage>> {
        return getMessagesFromApi(channelId, (currentTime - SINCE_TIME).toString())
    }

    override fun getChannelsList(): Single<List<Channel>> = channelsApi.getChannelsList()
            .map { it.channelList }

    override fun uploadFileToChannel(channelName: String, fileByteArray: ByteArray): Completable {
        return channelsApi.uploadFileToChannel(channelName, createMultipartBody(fileByteArray))
                .flatMapCompletable(this::parseResponse)
    }

    override fun getMessagesFromApi(channelId: String, sinceTime: String): Single<List<ChannelMessage>> {
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

    private fun getHistoryMessagesOnIOFromAPI(channelId: String, latestTimestamp: String?, oldestTimestamp: String?): Single<ChannelHistory> {
        return channelsApi.getChannelsHistory(channelId, COUNT, latestTimestamp, oldestTimestamp)
                .compose(RxTransformers.applySingleIoSchedulers())
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

    private fun addLists(a: List<ChannelMessage>, b: List<ChannelMessage>): List<ChannelMessage> {
        val list: MutableList<ChannelMessage> = mutableListOf()
        list.addAll(a)
        list.addAll(b)
        return list.toList()
    }

    override fun countChannelStatistics(channelId: String, channelName: String, user: String): Single<ChannelStatistics> {

        return getMessagesForChannel(channelId)
                .subscribeOn(Schedulers.computation())
                .flatMapObservable { Observable.fromIterable(it) }
                .collect({ ChannelCount(user) }, { t1: ChannelCount, t2: ChannelMessage? -> t1.accept(t2) })
                .flatMap { Single.just(ChannelStatistics(channelId, channelName, it.totalMessageCount, it.hereCount, it.mentionsCount, it.myMessageCount)) }
                .doAfterSuccess { channelsDao.insertChannel(it) }
    }

    class ChannelCount(val user: String, var hereCount: Int = 0, var mentionsCount: Int = 0, var myMessageCount: Int = 0, var totalMessageCount: Int = 0) {

        fun accept(channelMessage: ChannelMessage?): ChannelCount {
            channelMessage?.let {
                totalMessageCount++
                if (channelMessage.text.contains(ChannelMessage.HERE_TAG)) hereCount++
                if (channelMessage.text.contains(String.format(ChannelMessage.USER_MENTION, user))) mentionsCount++
                if (channelMessage.user.contains(user)) myMessageCount++
            }
            return this
        }
    }
}