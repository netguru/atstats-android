package co.netguru.android.socialslack.feature.search

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.channels.ChannelsComparator
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.search.adapter.SearchItemType
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class SearchPresenter @Inject constructor(private val channelsDao: ChannelsDao)
    : MvpNullObjectBasePresenter<SearchContract.View>(), SearchContract.Presenter {

    companion object {
        private const val MOCKED_ITEMS_COUNT = 50
    }

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun searchItemReceived(searchItemType: SearchItemType) {
        when (searchItemType) {
            SearchItemType.USERS -> view.initUsersSearchView(getMockedUsers())
            SearchItemType.CHANNELS -> getChannelsList()
        }
    }

    private fun getChannelsList() {
        view.showProgressBar()
        compositeDisposable += channelsDao.getAllChannels()
                .flatMap { sortChannelsList(it) }
                .compose(RxTransformers.applySingleIoSchedulers())
                .doAfterTerminate { view.hideProgressBar() }
                .subscribeBy(
                        onSuccess = {
                            view.initChannelSearchView(it)
                        },
                        onError = {
                            Timber.e(it, "Error while getting channels")
                            view.showError()
                        })
    }

    private fun sortChannelsList(channelList: List<ChannelStatistics>): Single<List<ChannelStatistics>> {
        return Observable.fromIterable(channelList)
                .toSortedList(ChannelsComparator.getChannelsComparatorForFilterOption(ChannelsFilterOption.MOST_ACTIVE_CHANNEL))
    }

    //TODO 29.08.2017 Remove those functions when integrating API
    private fun getMockedUsers() = 0.until(MOCKED_ITEMS_COUNT).map {
        UserStatistic("", "user $it", "", "", "User User$it",
                0, 0, (1000 + it), 0, null
        )
    }
}