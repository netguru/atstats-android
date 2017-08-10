package co.netguru.android.socialslack.feature.home.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.direct.DirectMessagesController
import co.netguru.android.socialslack.data.direct.model.ChannelStatistic
import co.netguru.android.socialslack.data.direct.model.DirectChannel
import co.netguru.android.socialslack.data.direct.model.DirectChannelWithMessages
import co.netguru.android.socialslack.data.direct.model.DirectMessage
import co.netguru.android.socialslack.data.user.UsersController
import co.netguru.android.socialslack.data.user.model.UserStatistic.Companion.toStatisticsView
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class HomeUsersPresenter @Inject constructor(
        private val directMessagesController: DirectMessagesController,
        private val usersController: UsersController)
    : MvpNullObjectBasePresenter<HomeUsersContract.View>(), HomeUsersContract.Presenter {

    private companion object {
        val MAX_MESSAGES_PER_REQUEST = 1000
        val USERS_SHOWN_IN_STATISTICS = 3
    }

    private val compositeDisposable = CompositeDisposable()

    override fun attachView(view: HomeUsersContract.View) {
        super.attachView(view)
        fetchMessages()
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    private fun fetchMessages() {
        directMessagesController.getDirectMessagesList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .flattenAsFlowable { x -> x }
                .flatMapSingle { fetchMessagesInConversation(it) }
                .toList()
                .subscribeBy(onSuccess = this::processUserData, onError = { it.printStackTrace() })
    }

    private fun processUserData(channels: List<DirectChannelWithMessages>) {
        findUserWeTalkTheMost(channels)
        findOtherUsersStatistics(channels)
    }

    private fun findOtherUsersStatistics(channels: List<DirectChannelWithMessages>) {
        val channelsWithStatistics: MutableList<ChannelStatistic> = mutableListOf()

        for ((directChannel, messages) in channels) {
            var messagesFromUs: Int = 0
            var messagesFromOtherUser: Int = 0

            for ((user) in messages) {
                if (user == directChannel.userId) {
                    messagesFromOtherUser++
                } else {
                    messagesFromUs++
                }
            }

            val channelStatistic = ChannelStatistic(directChannel,
                    messagesFromUs, messagesFromOtherUser)
            channelsWithStatistics.add(channelStatistic)
        }

        findUserWeWriteMost(channelsWithStatistics.toList())
        findUserThatWritesToUsMost(channelsWithStatistics.toList())
    }

    private fun findUserWeWriteMost(channelStatistics: List<ChannelStatistic>) {
        val sortedChannels = channelStatistics.sortedWith(Comparator<ChannelStatistic> { o1, o2
            -> o2.messagesFromUs.compareTo(o1.messagesFromUs) })

        Flowable.range(0, USERS_SHOWN_IN_STATISTICS)
                .flatMap({ usersController.getUserInfo(sortedChannels[it].channel.userId).toFlowable() },
                        { index, user -> user.toStatisticsView(sortedChannels[index].messagesFromUs) })
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersWeWriteMost, onError = { it.printStackTrace() })
    }

    private fun findUserThatWritesToUsMost(channelStatistics: List<ChannelStatistic>) {
        val sortedChannels = channelStatistics.sortedWith(Comparator<ChannelStatistic> { o1, o2 ->
            o2.messagesFromOtherUser.compareTo(o1.messagesFromOtherUser)
        })

        Flowable.range(0, USERS_SHOWN_IN_STATISTICS)
                .flatMap({ usersController.getUserInfo(sortedChannels[it].channel.userId).toFlowable() },
                        { index, user -> user.toStatisticsView(sortedChannels[index].messagesFromOtherUser) })
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersThatWriteToUsTheMost, onError = { it.printStackTrace() })
    }

    private fun findUserWeTalkTheMost(channels: List<DirectChannelWithMessages>) {
        val sortedChannels = channels.sortedWith(Comparator<DirectChannelWithMessages> { o1, o2 ->
            o2.messages.size.compareTo(o1.messages.size) })

        Flowable.range(0, USERS_SHOWN_IN_STATISTICS)
                .flatMap({ usersController.getUserInfo(sortedChannels[it].directChannel.userId).toFlowable() },
                        { index, user -> user.toStatisticsView(sortedChannels[index].messages.size) })
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersWeTalkTheMost, onError = { Timber.e(it) })
    }

    private fun fetchMessagesInConversation(directChannel: DirectChannel): Single<DirectChannelWithMessages> {
        var latestTimestamp: String? = null

        return Flowable.range(0, Int.MAX_VALUE)
                .concatMap { getMessagesOnIO(directChannel.id, latestTimestamp).toFlowable() }
                .doOnNext { if (it.isNotEmpty()) latestTimestamp = it.last().timeStamp }
                .takeUntil { it.size != MAX_MESSAGES_PER_REQUEST }
                .scan(this::addLists)
                .lastOrError()
                .map { DirectChannelWithMessages(directChannel, it) }
    }

    private fun getMessagesOnIO(inChannel: String, latestTimestamp: String?): Single<List<DirectMessage>> {
        return directMessagesController.getMessagesInDirectChannel(inChannel,
                latestTimestamp, MAX_MESSAGES_PER_REQUEST)
                .compose(RxTransformers.applySingleIoSchedulers())
    }

    private fun addLists(a: List<DirectMessage>, b: List<DirectMessage>): List<DirectMessage> {
        val list: MutableList<DirectMessage> = mutableListOf<DirectMessage>()
        list.addAll(a)
        list.addAll(b)
        return list.toList()
    }
}