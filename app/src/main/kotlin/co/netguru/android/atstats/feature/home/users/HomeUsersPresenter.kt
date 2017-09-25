package co.netguru.android.atstats.feature.home.users

import co.netguru.android.atstats.app.scope.FragmentScope
import co.netguru.android.atstats.common.util.RxTransformers
import co.netguru.android.atstats.data.direct.DirectChannelsDao
import co.netguru.android.atstats.data.direct.model.DirectChannelStatistics
import co.netguru.android.atstats.data.filter.directchannel.DirectChannelsComparator
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import co.netguru.android.atstats.data.user.UsersController
import co.netguru.android.atstats.data.user.model.User
import co.netguru.android.atstats.data.user.model.UserStatistic
import co.netguru.android.atstats.data.user.model.UserStatistic.Companion.toStatisticsView
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
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
        private const val NUMBER_OF_SUBSCRIBER = 3
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
        val userStream = directChannelsDao.getAllDirectChannels()
                .toFlowable()
                .publish()
                .autoConnect(NUMBER_OF_SUBSCRIBER)
                .lastOrError()

        findUserWeTalkTheMost(userStream)
        findUserWeWriteMost(userStream)
        findUserThatWritesToUsMost(userStream)
    }

    private fun findUserWeWriteMost(userStream: Single<List<DirectChannelStatistics>>) {
        compositeDisposable +=
                userStream.flatMap {
                    findUserWithFilter(it, UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST, this::toUserStatistics)
                }
                        .compose(RxTransformers.applySingleIoSchedulers())
                        .subscribeBy(onSuccess = view::setUsersWeWriteMost, onError = { it.printStackTrace() })
    }

    private fun findUserThatWritesToUsMost(userStream: Single<List<DirectChannelStatistics>>) {

        compositeDisposable += userStream.flatMap {
            findUserWithFilter(it, UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST, this::toUserStatistics)
        }
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersThatWriteToUsTheMost, onError = { it.printStackTrace() })
    }

    private fun findUserWeTalkTheMost(userStream: Single<List<DirectChannelStatistics>>) {

        compositeDisposable += userStream.flatMap {
            findUserWithFilter(it, UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST, this::toUserStatistics)
        }
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(onSuccess = view::setUsersWeTalkTheMost, onError = { Timber.e(it) })
    }

    private fun findUserWithFilter(channelStatistics: List<DirectChannelStatistics>,
                                   filter: UsersFilterOption,
                                   toUserStatistics: (DirectChannelStatistics, User) -> UserStatistic) = Single.just(channelStatistics)
            .zipWith(Single.just(filter))
            { statisticsList, filterOption -> Pair(statisticsList, filterOption) }
            .map(this::sortWithFilter)
            .flattenAsFlowable { it }
            .flatMap({ usersController.getUserInfo(it.userId).toFlowable() },
                    toUserStatistics)
            .toList()
            .compose(RxTransformers.applySingleIoSchedulers())

    private fun sortWithFilter(listFilter: Pair<List<DirectChannelStatistics>, UsersFilterOption>) =
            listFilter.first
                    .sortedWith(DirectChannelsComparator.getFirectChannelComparatorForFilterOption(listFilter.second))
                    .subList(0, USERS_SHOWN_IN_STATISTICS)

    private fun toUserStatistics(channelStatistics: DirectChannelStatistics, user: User)
            = user.toStatisticsView(channelStatistics.messagesFromUs, channelStatistics.messagesFromOtherUser, channelStatistics.isCurrentUser)
}