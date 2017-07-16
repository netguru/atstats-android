package co.netguru.android.socialslack.feature.home.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.direct.DirectMessagesController
import co.netguru.android.socialslack.data.direct.model.ChannelStatistic
import co.netguru.android.socialslack.data.direct.model.DirectChannel
import co.netguru.android.socialslack.data.direct.model.DirectChannelWithMessages
import co.netguru.android.socialslack.data.direct.model.Message
import co.netguru.android.socialslack.data.user.UsersController
import co.netguru.android.socialslack.data.user.model.UserStatistic.Companion.toStatisticsView
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

@FragmentScope
class HomeUsersPresenter @Inject constructor(
        private val directMessagesController: DirectMessagesController,
        private val usersController: UsersController)
    : MvpNullObjectBasePresenter<HomeUsersContract.View>(), HomeUsersContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    companion object {
        val MAX_MESSAGES_PER_REQUEST = 1000
        val USERS_SHOWN_IN_STATISTICS = 3
    }

    override fun attachView(view: HomeUsersContract.View) {
        super.attachView(view)
        fetchMessages()
    }

    private fun fetchMessages() {
        directMessagesController.getDirectMessagesList()
                .compose(RxTransformers.applyFlowableIoSchedulers())
                .flatMapIterable { x -> x }
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

        for (channel in channels) {
            var messagesFromUs: Int = 0
            var messagesFromOtherUser: Int = 0

            for (message in channel.messages) {
                if (message.user == null || message.user == channel.directChannel.user) {
                    messagesFromOtherUser++
                } else {
                    messagesFromUs++
                }
            }
            val channelStatistic = ChannelStatistic(channel.directChannel,
                    messagesFromUs, messagesFromOtherUser)
            channelsWithStatistics.add(channelStatistic)
        }

        findUserWeWriteMost(channelsWithStatistics.toList())
        findUserThatWritesToUsMost(channelsWithStatistics.toList())
    }

    private fun findUserWeWriteMost(channelStatistics: List<ChannelStatistic>) {
        val sortedChannels = channelStatistics.sortedWith(object : Comparator<ChannelStatistic> {
            override fun compare(o1: ChannelStatistic, o2: ChannelStatistic): Int {
                return o2.messagesFromUs.compareTo(o1.messagesFromUs)
            }
        })

        Flowable.range(0, USERS_SHOWN_IN_STATISTICS)
                .flatMap({ usersController.getUserInfo(sortedChannels[it].channel.user).toFlowable() },
                        { index, user -> user.toStatisticsView(sortedChannels[index].messagesFromUs) })
                .compose(RxTransformers.applyFlowableIoSchedulers())
                .toList()
                .subscribeBy(onSuccess = view::setUsersWeWriteMost, onError = { it.printStackTrace() })
    }

    private fun findUserThatWritesToUsMost(channelStatistics: List<ChannelStatistic>) {
        val sortedChannels = channelStatistics.sortedWith(object : Comparator<ChannelStatistic> {
            override fun compare(o1: ChannelStatistic, o2: ChannelStatistic): Int {
                return o2.messagesFromOtherUser.compareTo(o1.messagesFromOtherUser)
            }
        })

        Flowable.range(0, USERS_SHOWN_IN_STATISTICS)
                .flatMap({ usersController.getUserInfo(sortedChannels[it].channel.user).toFlowable() },
                        { index, user -> user.toStatisticsView(sortedChannels[index].messagesFromOtherUser) })
                .compose(RxTransformers.applyFlowableIoSchedulers())
                .toList()
                .subscribeBy(onSuccess = view::setUsersThatWriteToUsTheMost, onError = { it.printStackTrace() })
    }

    private fun findUserWeTalkTheMost(channels: List<DirectChannelWithMessages>) {
        val sortedChannels = channels.sortedWith(object : Comparator<DirectChannelWithMessages> {
            override fun compare(o1: DirectChannelWithMessages, o2: DirectChannelWithMessages): Int {
                return o2.messages.size.compareTo(o1.messages.size)
            }
        })

        Flowable.range(0, USERS_SHOWN_IN_STATISTICS)
                .flatMap({ usersController.getUserInfo(sortedChannels[it].directChannel.user).toFlowable() },
                        { index, user -> user.toStatisticsView(sortedChannels[index].messages.size) })
                .compose(RxTransformers.applyFlowableIoSchedulers())
                .toList()
                .subscribeBy(onSuccess = view::setUsersWeTalkTheMost, onError = { it.printStackTrace() })
    }

    private fun fetchMessagesInConversation(directChannel: DirectChannel): Single<DirectChannelWithMessages> {
        var latestTimestamp: String? = null

        return Flowable.range(0, Int.MAX_VALUE)
                .concatMap { getMessagesOnIO(directChannel.id, latestTimestamp) }
                .doOnNext { if (it.isNotEmpty()) latestTimestamp = it.last().timeStamp }
                .takeUntil { it.size != MAX_MESSAGES_PER_REQUEST }
                .scan(this::addLists)
                .lastOrError()
                .map { DirectChannelWithMessages(directChannel, it) }
    }

    private fun getMessagesOnIO(inChannel: String, latestTimestamp: String?): Flowable<List<Message>> {
        return directMessagesController.getMessagesInDirectChannel(inChannel,
                latestTimestamp, MAX_MESSAGES_PER_REQUEST)
                .compose(RxTransformers.applyFlowableIoSchedulers())
    }

    private fun addLists(a: List<Message>, b: List<Message>): List<Message> {
        val list: MutableList<Message> = mutableListOf<Message>()
        list.addAll(a)
        list.addAll(b)
        return list.toList()
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

}