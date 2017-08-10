package co.netguru.android.socialslack.data.direct

import co.netguru.android.socialslack.data.direct.model.DirectChannel
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatisticsCount
import co.netguru.android.socialslack.data.direct.model.DirectMessage
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DirectMessagesController @Inject constructor(private val directMessagesApi: DirectMessagesApi,
                                                   private val directMessagesDao: DirectChannelsDao) {

    companion object {
        const val MESSAGES_INCLUSIVE = 0
        private const val COUNT = 1000
        private const val SINCE_TIME: Long = 60 * 60 * 24 * 30 // 30 days in seconds
        private val currentTime = System.currentTimeMillis() / 1000
    }

    fun getDirectMessagesList(): Single<List<DirectChannel>> =
            directMessagesApi.getDirectMessagesList()
                    .map { it.channels }

    fun countDirectChannelStatistics(channelId: String, userId: String): Single<DirectChannelStatistics> =
        getAllMessagesFromApi(channelId)
                .observeOn(Schedulers.computation())
                .flattenAsObservable { it }
                .collect({DirectChannelStatisticsCount(userId)},
                        {t1: DirectChannelStatisticsCount?, t2: DirectMessage? ->  t1?.accept(t2)})
                .map { DirectChannelStatistics(channelId, userId, it.messagesFromUs, it.messagesFromOtherUser) }
                .observeOn(Schedulers.io())
                .doAfterSuccess { directMessagesDao.insertDirectChannel(it) }

   private fun getAllMessagesFromApi(channelId: String) =
            getMessagesFromApi(channelId, SINCE_TIME.toString())
                    .subscribeOn(Schedulers.io())

    private fun getMessagesFromApi(channelId: String, sinceTime: String): Single<List<DirectMessage>> {
        var lastTimestamp = currentTime.toString()

        return Flowable.range(0, Int.MAX_VALUE)
                .concatMap {
                    getMessagesInIOFromApi(channelId, lastTimestamp, sinceTime)
                            .toFlowable()
                }
                .doOnNext { if (it.messages.isNotEmpty()) lastTimestamp = it.messages.last().timeStamp }
                .takeUntil { !it.hasMore }
                .map { it.messages }
                .scan(this::addLists)
                .lastOrError()
    }

    private fun addLists(a: List<DirectMessage>, b: List<DirectMessage>): List<DirectMessage> {
        val list: MutableList<DirectMessage> = mutableListOf<DirectMessage>()
        list.addAll(a)
        list.addAll(b)
        return list.toList()
    }

    private fun getMessagesInIOFromApi(channelId: String, latestTimestamp: String, oldestTimestamp: String) =
            directMessagesApi.getDirectMessagesWithUser(channelId, COUNT, latestTimestamp, oldestTimestamp)
                    .subscribeOn(Schedulers.io())

    fun getMessagesInDirectChannel(channel: String, latest: String?, count: Int): Single<List<DirectMessage>> {
        if (latest == null) {
            return directMessagesApi.getDirectMessagesWithUser(channel, count)
                    .map { it.messages }
        } else {
            return directMessagesApi.getDirectMessagesWithUser(channel, count, latest, MESSAGES_INCLUSIVE)
                    .map { it.messages }
        }
    }
}