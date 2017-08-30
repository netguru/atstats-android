package co.netguru.android.socialslack.feature.search

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.channels.ChannelsComparator
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.user.UsersController
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
class SearchPresenter @Inject constructor(private val channelsDao: ChannelsDao, private val usersController: UsersController)
    : MvpNullObjectBasePresenter<SearchContract.View>(), SearchContract.Presenter {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var searchItemType: SearchItemType

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun searchItemReceived(searchItemType: SearchItemType) {
        this.searchItemType = searchItemType
        when (searchItemType) {
            SearchItemType.USERS -> getUsersList()
            SearchItemType.CHANNELS -> getChannelsList()
        }
    }

    override fun searchQueryReceived(query: String) {
        Timber.d("Received search query: $query")
        when(searchItemType) {
            SearchItemType.USERS -> view.filterUsersList(query)
            SearchItemType.CHANNELS -> view.filterChannelsList(query)
        }
    }

    private fun getUsersList() {
        view.showProgressBar()
        compositeDisposable += usersController.getUsersList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .doAfterTerminate { view.hideProgressBar() }
                .subscribeBy(
                        onSuccess = {
                            view.initUsersSearchView(it)
                        },
                        onError = {
                            Timber.e(it, "Error while getting users list")
                            view.showError()
                        })
    }

    private fun getChannelsList() {
        //TODO 30.08.2017 For now we are getting only channels in which current user is member
        //TODO 30.08.2017 Current there is no possibility to get all channels with messages, because of API limitations
        //TODO 30.08.2017 https://api.slack.com/docs/rate-limits
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
}