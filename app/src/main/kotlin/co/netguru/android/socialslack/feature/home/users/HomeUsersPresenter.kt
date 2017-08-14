package co.netguru.android.socialslack.feature.home.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.direct.DirectChannelsDao
import co.netguru.android.socialslack.data.direct.model.DirectChannelStatistics
import co.netguru.android.socialslack.data.filter.directchannel.DirectChannelsComparator
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.UsersController
import co.netguru.android.socialslack.data.user.model.User
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.data.user.model.UserStatistic.Companion.toStatisticsView
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

        compositeDisposable += findUserWithFilter(channelStatistics,
                UsersFilterOption.PERSON_WHO_WE_WRITE_THE_MOST,
                this::toUserStatisticsThatWeWriteMost)
                .subscribeBy(onSuccess = view::setUsersWeWriteMost, onError = { it.printStackTrace() })
    }

    private fun findUserThatWritesToUsMost(channelStatistics: List<DirectChannelStatistics>) {

        compositeDisposable += findUserWithFilter(channelStatistics,
                UsersFilterOption.PERSON_WHO_WRITES_TO_US_THE_MOST,
                this::toUserStatisticsThatWritesToUsMost)
                .subscribeBy(onSuccess = view::setUsersThatWriteToUsTheMost, onError = { it.printStackTrace() })
    }

    private fun findUserWeTalkTheMost(channelStatistics: List<DirectChannelStatistics>) {

        compositeDisposable += findUserWithFilter(channelStatistics,
                UsersFilterOption.PERSON_WHO_WE_TALK_THE_MOST,
                this::toUserStatisticsThatTalkMost)
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

    private fun toUserStatisticsThatWeWriteMost(channelStatistics: DirectChannelStatistics, user: User)
            = user.toStatisticsView(channelStatistics.messagesFromUs)

    private fun toUserStatisticsThatWritesToUsMost(channelStatistics: DirectChannelStatistics, user: User)
            = user.toStatisticsView(channelStatistics.messagesFromOtherUser)

    private fun toUserStatisticsThatTalkMost(channelStatistics: DirectChannelStatistics, user: User) = user.toStatisticsView(
            channelStatistics.messagesFromOtherUser + channelStatistics.messagesFromUs,
            channelStatistics.messagesFromUs,
            channelStatistics.messagesFromOtherUser)
}