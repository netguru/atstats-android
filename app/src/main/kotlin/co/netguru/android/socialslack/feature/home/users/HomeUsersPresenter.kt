package co.netguru.android.socialslack.feature.home.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.user.UsersController
import co.netguru.android.socialslack.data.user.model.UserStatistic.Companion.toStatisticsView
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class HomeUsersPresenter @Inject constructor(
        private val usersController: UsersController,
        private val directChannelsDao: DirectChannelsDao)
    : MvpNullObjectBasePresenter<HomeUsersContract.View>(), HomeUsersContract.Presenter {

    private companion object {
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
        directChannelsDao.getAllDirectChannels()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = this::processUserData, onError = { it.printStackTrace() })
    }

    private fun processUserData(channels: List<DirectChannelStatistics>) {
        findUserWeTalkTheMost(channels)
        findUserWeWriteMost(channels)
        findUserThatWritesToUsMost(channels)
    }

    private fun findUserWeWriteMost(channelStatistics: List<DirectChannelStatistics>) {
        val sortedChannels = channelStatistics.sortedWith(Comparator<DirectChannelStatistics> { o1, o2
            ->
            o2.messagesFromUs.compareTo(o1.messagesFromUs)
        })

        Flowable.range(0, USERS_SHOWN_IN_STATISTICS)
                .flatMap({ usersController.getUserInfo(sortedChannels[it].userId).toFlowable() },
                        { index, user -> user.toStatisticsView(sortedChannels[index].messagesFromUs) })
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersWeWriteMost, onError = { it.printStackTrace() })
    }

    private fun findUserThatWritesToUsMost(channelStatistics: List<DirectChannelStatistics>) {
        val sortedChannels = channelStatistics.sortedWith(Comparator<DirectChannelStatistics> { o1, o2 ->
            o2.messagesFromOtherUser.compareTo(o1.messagesFromOtherUser)
        })

        Flowable.range(0, USERS_SHOWN_IN_STATISTICS)
                .flatMap({ usersController.getUserInfo(sortedChannels[it].userId).toFlowable() },
                        { index, user -> user.toStatisticsView(sortedChannels[index].messagesFromOtherUser) })
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersThatWriteToUsTheMost, onError = { it.printStackTrace() })
    }

    private fun findUserWeTalkTheMost(channels: List<DirectChannelStatistics>) {
        val sortedChannels = channels.sortedWith(Comparator<DirectChannelStatistics> { o1, o2 ->
            (o2.messagesFromOtherUser + o2.messagesFromUs).compareTo((o1.messagesFromOtherUser + o1.messagesFromUs))
        })

        Flowable.range(0, USERS_SHOWN_IN_STATISTICS)
                .flatMap({ usersController.getUserInfo(sortedChannels[it].userId).toFlowable() },
                        { index, user ->
                            user.toStatisticsView(
                                    sortedChannels[index].messagesFromOtherUser + sortedChannels[index].messagesFromUs,
                                    sortedChannels[index].messagesFromUs,
                                    sortedChannels[index].messagesFromOtherUser
                            )
                        })
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersWeTalkTheMost, onError = { Timber.e(it) })
    }
}