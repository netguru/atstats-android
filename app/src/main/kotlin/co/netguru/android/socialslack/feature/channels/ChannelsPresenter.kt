package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.channels.ChannelsComparator
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.channels.ChannelsPositionUpdater
import co.netguru.android.socialslack.data.filter.channels.ChannelsStatisticsNumberChecker
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.share.SharableListProvider
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
class ChannelsPresenter @Inject constructor(private val channelsDao: ChannelsDao,
                                            private val filterController: FilterController)
    : MvpNullObjectBasePresenter<ChannelsContract.View>(), ChannelsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun getCurrentFilterOption() {
        compositeDisposable += filterController.getChannelsFilterOption()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.setCurrentFilterOption(it)
                        },
                        onError = {
                            Timber.e(it, "Error while getting current filter option")
                            view.showFilterOptionError()
                        }
                )
    }

    override fun getChannels() {
        view.showLoadingView()
        compositeDisposable += channelsDao.getAllChannels()
                .zipWith(filterController.getChannelsFilterOption())
                { channelsList, filterOption -> Pair(channelsList, filterOption) }
                .flatMap { filterAndSortChannelsList(it.first, it.second) }
                .compose(RxTransformers.applySingleComputationSchedulers())
                .doAfterTerminate { view.hideLoadingView() }
                .subscribeBy(
                        onSuccess = {
                            view.showChannels(it.first)
                        },
                        onError = {
                            Timber.e(it, "Error while getting channels from server")
                            view.showError()
                        })
    }

    override fun filterButtonClicked() {
        view.showFilterView()
    }

    override fun searchButtonClicked() {
        view.showSearchView()
    }

    override fun sortRequestReceived() {
        view.showLoadingView()
        compositeDisposable += channelsDao.getAllChannels()
                .zipWith(filterController.getChannelsFilterOption())
                { channelsList, filterOption -> Pair(channelsList, filterOption) }
                .flatMap { filterAndSortChannelsList(it.first, it.second) }
                .compose(RxTransformers.applySingleIoSchedulers())
                .doAfterTerminate { view.hideLoadingView() }
                .subscribeBy(
                        onSuccess = { (channelsList, filterOption) ->
                            view.showChannels(channelsList)
                            view.setCurrentFilterOption(filterOption)
                        },
                        onError = {
                            Timber.e(it, "Error while changing channels order")
                            view.showFilterOptionError()
                        })
    }

    override fun onChannelClick(selectedItemPosition: Int, channelList: List<ChannelStatistics>) {
        compositeDisposable += Single.just(channelList)
                .map { SharableListProvider.getSharableList(selectedItemPosition, channelList) }
                .map { it.filterIsInstance(ChannelStatistics::class.java) }
                .zipWith(filterController.getChannelsFilterOption())
                { channelsList, filterOption -> Pair(channelsList, filterOption) }
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = { (channelsList, filterOption) ->
                            view.showChannelDetails(channelList[selectedItemPosition], channelsList, filterOption)
                        },
                        onError = {
                            Timber.e(it, "Error while getting sharable channels list")
                        })
    }

    private fun filterAndSortChannelsList(channelList: List<ChannelStatistics>, channelsFilterOption: ChannelsFilterOption): Single<Pair<List<ChannelStatistics>, ChannelsFilterOption>> {
        return Observable.fromIterable(channelList)
                .filter { ChannelsStatisticsNumberChecker.checkStatisticsNumberForFilterOption(channelsFilterOption, it) }
                .toSortedList(ChannelsComparator.getChannelsComparatorForFilterOption(channelsFilterOption))
                .doOnSuccess { ChannelsPositionUpdater.updateChannelsPositionInList(it, channelsFilterOption) }
                .map { Pair(it, channelsFilterOption) }
    }
}