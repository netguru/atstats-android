package co.netguru.android.socialslack.feature.home.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.direct.DirectChannelsController
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.filter.directchannel.DirectChannelsComparator
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.filter.users.UsersComparator
import co.netguru.android.socialslack.data.filter.users.UsersPositionUpdater
import co.netguru.android.socialslack.data.user.UsersController
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.data.user.model.UserStatistic.Companion.toStatisticsView
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.zipWith
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
        compositeDisposable += directChannelsDao.getAllDirectChannels()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = this::processUserData, onError = { it.printStackTrace() })
    }

    private fun processUserData(channels: List<DirectChannelStatistics>) {
        findUserWeTalkTheMost(channels)
        findUserWeWriteMost(channels)
        findUserThatWritesToUsMost(channels)
    }

    private fun findUserWeWriteMost(channelStatistics: List<DirectChannelStatistics>) {

        compositeDisposable += Single.just(channelStatistics)
                .zipWith(Single.just(UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST))
                { statisticsList, filterOption -> Pair(statisticsList, filterOption) }
                .map (this::sortWithFilter)
                .flattenAsFlowable { it }
                .flatMap({ usersController.getUserInfo(it.userId).toFlowable() },
                        { statistics, user -> user.toStatisticsView(statistics.messagesFromUs) })
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersWeWriteMost, onError = { it.printStackTrace() })
    }

    private fun findUserThatWritesToUsMost(channelStatistics: List<DirectChannelStatistics>) {

        compositeDisposable += Single.just(channelStatistics)
                .zipWith(Single.just(UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST))
                { statisticsList, filterOption -> Pair(statisticsList, filterOption) }
                .map (this::sortWithFilter)
                .flattenAsFlowable { it }
                .flatMap({ usersController.getUserInfo(it.userId).toFlowable() },
                        { statistics, user -> user.toStatisticsView(statistics.messagesFromOtherUser) })
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersThatWriteToUsTheMost, onError = { it.printStackTrace() })
    }

    private fun findUserWeTalkTheMost(channelStatistics: List<DirectChannelStatistics>) {

        compositeDisposable += Single.just(channelStatistics)
                .zipWith(Single.just(UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST))
                { statisticsList, filterOption -> Pair(statisticsList, filterOption) }
                .map (this::sortWithFilter)
                .flattenAsFlowable { it }
                .flatMap({ usersController.getUserInfo(it.userId).toFlowable() },
                        { statistics, user ->
                            user.toStatisticsView(
                                    statistics.messagesFromOtherUser + statistics.messagesFromUs,
                                    statistics.messagesFromUs,
                                    statistics.messagesFromOtherUser
                            )
                        })
                .toList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersWeTalkTheMost, onError = { Timber.e(it) })
    }

    private fun sortWithFilter(listFilter: Pair<List<DirectChannelStatistics>, UsersFilterOption>) =
            listFilter.first
                    .sortedWith(DirectChannelsComparator.getFirectChannelComparatorForFilterOption(listFilter.second))
                    .subList(0, USERS_SHOWN_IN_STATISTICS)

}