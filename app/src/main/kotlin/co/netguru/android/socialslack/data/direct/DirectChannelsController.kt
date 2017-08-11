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
import javax.inject.Inject

@UserScope
class DirectChannelsController @Inject constructor(private val directChannelsApi: DirectChannnelsApi,
                                                   private val directChannelsDao: DirectChannelsDao) {

    fun getDirectChannelsList(): Single<List<DirectChannel>> =
            directChannelsApi.getDirectMessagesList()
                    .map { it.channels }

    fun countDirectChannelStatistics(channelId: String, userId: String): Single<DirectChannelStatistics> =
            getAllMessagesFromApi(channelId)
                    .observeOn(Schedulers.computation())
                    .flattenAsObservable { it }
                    .collect({ DirectChannelStatisticsCount(userId) },
                            { t1: DirectChannelStatisticsCount?, t2: DirectMessage? -> t1?.accept(t2) })
                    .map { DirectChannelStatistics(channelId, userId, it.messagesFromUs, it.messagesFromOtherUser) }
                    .observeOn(Schedulers.io())
                    .doAfterSuccess { directChannelsDao.insertDirectChannel(it) }

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

    fun sortUserWeWriteMost(channelStatistics: List<DirectChannelStatistics>? = null,
                            numberToShow: Int? = null): Single<List<DirectChannelStatistics>> =
            sortWithComparatorAndReturn(
                    channelStatistics,
                    numberToShow,
                    this::userWeWriteMostComparator
            )

    fun sortUserThatWritesToUsMost(channelStatistics: List<DirectChannelStatistics>? = null,
                                   numberToShow: Int? = null): Single<List<DirectChannelStatistics>> =
            sortWithComparatorAndReturn(
                    channelStatistics,
                    numberToShow,
                    this::userThatWritesToUsMostComparator
            )

    fun sortUserWeTalkTheMost(channelStatistics: List<DirectChannelStatistics>? = null,
                              numberToShow: Int? = null): Single<List<DirectChannelStatistics>> =
            sortWithComparatorAndReturn(
                    channelStatistics,
                    numberToShow,
                    this::userWeTalkTheMostComparator
            )

    private fun sortWithComparatorAndReturn(channelStatistics: List<DirectChannelStatistics>?,
                                            numberToShow: Int?,
                                            comparator: (DirectChannelStatistics, DirectChannelStatistics) -> Int)
            : Single<List<DirectChannelStatistics>> {

        val singleChannelStatistics =
                if (channelStatistics != null)
                    Single.just(channelStatistics)
                else
                    directChannelsDao.getAllDirectChannels()

        return singleChannelStatistics
                .map {
                    it.sortedWith(Comparator<DirectChannelStatistics> { o1, o2
                        ->
                        comparator(o1, o2)
                    })
                }
                .map {
                    if (numberToShow != null) it.subList(0, numberToShow) else it
                }
    }

    private fun userWeWriteMostComparator(o1: DirectChannelStatistics, o2: DirectChannelStatistics) =
            o2.messagesFromUs.compareTo(o1.messagesFromUs)

    private fun userThatWritesToUsMostComparator(o1: DirectChannelStatistics, o2: DirectChannelStatistics) =
            o2.messagesFromOtherUser.compareTo(o1.messagesFromOtherUser)

    private fun userWeTalkTheMostComparator(o1: DirectChannelStatistics, o2: DirectChannelStatistics) =
            (o2.messagesFromOtherUser + o2.messagesFromUs).compareTo((o1.messagesFromOtherUser + o1.messagesFromUs))
}