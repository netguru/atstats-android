package co.netguru.android.socialslack.data.direct

import co.netguru.android.socialslack.app.scope.UserScope
import co.netguru.android.socialslack.common.util.TimeAndCountUtil
import co.netguru.android.socialslack.data.direct.model.DirectChannel
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatisticsCount
import co.netguru.android.socialslack.data.direct.model.DirectMessage
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@UserScope
class DirectChannelsController @Inject constructor(private val directChannelsApi: DirectChannnelsApi,
                                                   private val directChannelsDao: DirectChannelsDao) {

    companion object {

        private const val HOURS_24_IN_SECONDS = 60 * 60 * 24 - 1

        private fun getMidnightTimestampInSeconds(): Long {
            val date = GregorianCalendar()
            with(date) {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            return date.timeInMillis / 1000
        }
    }

    fun getDirectChannelsList(): Single<List<DirectChannel>> =
            directChannelsApi.getDirectMessagesList()
                    .map { it.channels }

    fun countDirectChannelStatistics(channelId: String, userId: String): Single<DirectChannelStatistics> {
        var lastMidnight = getMidnightTimestampInSeconds()
        var streakDays = 0

        return getAllMessagesFromApi(channelId)
                .observeOn(Schedulers.computation())
                .flattenAsObservable { it }
                .doOnNext {
                    val messageTimestamp = it.timeStamp.toFloat()
                    // if there is a message from today and streak day wasn't count
                    if (streakDays < 1 && messageTimestamp > lastMidnight) {
                        // count as streak day
                        streakDays++
                    }
                    // if there is a message from 00:00:00 to 23:59:59 yesterday
                    if (messageTimestamp in (lastMidnight - HOURS_24_IN_SECONDS)..(lastMidnight - 1)) {
                        // Add a streak day and check if there is a message in the previous day
                        streakDays++
                        lastMidnight -= HOURS_24_IN_SECONDS
                    }
                }
                .collect({ DirectChannelStatisticsCount(userId) },
                        { t1: DirectChannelStatisticsCount?, t2: DirectMessage? -> t1?.accept(t2) })
                .map { DirectChannelStatistics(channelId, userId, it.messagesFromUs, it.messagesFromOtherUser, streakDays) }
                .observeOn(Schedulers.io())
                .doAfterSuccess { directChannelsDao.insertDirectChannel(it) }
    }

    private fun getAllMessagesFromApi(channelId: String) =
            getMessagesFromApi(channelId, (TimeAndCountUtil.currentTimeInSeconds() - TimeAndCountUtil.SINCE_TIME).toString())
                    .subscribeOn(Schedulers.io())

    private fun getMessagesFromApi(channelId: String, sinceTime: String): Single<List<DirectMessage>> {
        var lastTimestamp = TimeAndCountUtil.currentTimeInSeconds().toString()

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
        val list: MutableList<DirectMessage> = mutableListOf()
        list.addAll(a)
        list.addAll(b)
        return list.toList()
    }

    private fun getMessagesInIOFromApi(channelId: String, latestTimestamp: String, oldestTimestamp: String) =
            directChannelsApi.getDirectMessagesWithUser(channelId, TimeAndCountUtil.MESSAGE_COUNT, latestTimestamp, oldestTimestamp)
                    .subscribeOn(Schedulers.io())
}