package co.netguru.android.atstats.data.direct

import co.netguru.android.atstats.app.scope.UserScope
import co.netguru.android.atstats.common.util.TimeAndCountUtil
import co.netguru.android.atstats.data.direct.model.DirectChannel
import co.netguru.android.atstats.data.direct.model.DirectChannelStatistics
import co.netguru.android.atstats.data.direct.model.DirectChannelStatisticsCount
import co.netguru.android.atstats.data.direct.model.DirectMessage
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import javax.inject.Inject

@UserScope
class DirectChannelsController @Inject constructor(private val directChannelsApi: DirectChannnelsApi,
                                                   private val directChannelsDao: DirectChannelsDao) {

    companion object {
        private const val ONE_DAY_DIFF = 1
    }

    fun getDirectChannelsList(): Single<List<DirectChannel>> =
            directChannelsApi.getDirectMessagesList()
                    .map { it.channels }

    fun countDirectChannelStatistics(channelId: String, userId: String, isCurrentUser: Boolean): Single<DirectChannelStatistics> {
        // MaxStreakDays, CurrentStreakDays, LastMessageDayOfMonth
        var streakDaysMidnightTriple = Triple(1, 1, 0)

        return getAllMessagesFromApi(channelId)
                .observeOn(Schedulers.computation())
                .flattenAsObservable { it }
                .doOnNext { streakDaysMidnightTriple = countStreakDays(it, streakDaysMidnightTriple) }
                .collect({ DirectChannelStatisticsCount(userId) },
                        { t1: DirectChannelStatisticsCount?, t2: DirectMessage? -> t1?.accept(t2) })
                .map {
                    DirectChannelStatistics(channelId, userId, it.messagesFromUs, it.messagesFromOtherUser,
                            streakDaysMidnightTriple.first, isCurrentUser)
                }
                .observeOn(Schedulers.io())
                .doAfterSuccess { directChannelsDao.insertDirectChannel(it) }
    }

    private fun countStreakDays(directMessage: DirectMessage, streakDaysMidnightTriple: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
        val messageDay = getMessageDayFromTimeStamp(directMessage.timeStamp.toFloat().toLong())
        val currentMaxStreakDays = streakDaysMidnightTriple.first
        val lastMessageDay = if (streakDaysMidnightTriple.third == 0) messageDay else streakDaysMidnightTriple.third
        var currentStreakDays = streakDaysMidnightTriple.second

        if (lastMessageDay - messageDay == ONE_DAY_DIFF) {
            currentStreakDays++
        } else if (lastMessageDay - messageDay > ONE_DAY_DIFF) {
            currentStreakDays = 1
        }

        val maxStreakDays = if (currentStreakDays > currentMaxStreakDays) currentStreakDays else currentMaxStreakDays

        return Triple(maxStreakDays, currentStreakDays, messageDay)
    }

    private fun getMessageDayFromTimeStamp(timeStamp: Long) = LocalDateTime.ofEpochSecond(timeStamp, 0, ZoneOffset.UTC).dayOfYear

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