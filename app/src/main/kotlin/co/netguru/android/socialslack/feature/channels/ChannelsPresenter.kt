package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsDao
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.ChannelsComparator
import co.netguru.android.socialslack.data.filter.FilterController
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
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

    companion object {
        private const val MOST_ACTIVE_CHANNEL_NUMBER = 3
    }

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
                            view.setCurrentFilterOptionText(it.textResId)
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
                .flatMap { sortChannelsList(it.first, it.second) }
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

    override fun sortRequestReceived(channelList: List<ChannelStatistics>) {
        view.showLoadingView()
        compositeDisposable += Single.just(channelList)
                .zipWith(filterController.getChannelsFilterOption())
                { channelsList, filterOption -> Pair(channelsList, filterOption) }
                .flatMap { sortChannelsList(it.first, it.second) }
                .compose(RxTransformers.applySingleIoSchedulers())
                .doAfterTerminate { view.hideLoadingView() }
                .subscribeBy(
                        onSuccess = {
                            view.showChannels(it.first)
                            view.setCurrentFilterOptionText(it.second.textResId)
                        },
                        onError = {
                            Timber.e(it, "Error while changing channels order")
                            view.showFilterOptionError()
                        })
    }

    override fun onChannelClick(channelStatistics: ChannelStatistics, channelList: List<ChannelStatistics>) {
        compositeDisposable +=
                filterController.getChannelsFilterOption()
                        .flatMap { getProperStreamForCurrentFilterOption(it, channelStatistics, channelList) }
                        .compose(RxTransformers.applySingleComputationSchedulers())
                        .subscribeBy(
                                onSuccess = { (channel, channelsList) ->
                                    view.showChannelDetails(channel, channelsList)
                                },
                                onError = {
                                    Timber.e(it, "Error while getting three most active channels")
                                })
    }

    private fun getProperStreamForCurrentFilterOption(currentFilterOption: ChannelsFilterOption,
                                                      channelStatistics: ChannelStatistics, channelList: List<ChannelStatistics>
    ): Single<Pair<ChannelStatistics, List<ChannelStatistics>>> {

        return if (currentFilterOption == ChannelsFilterOption.MOST_ACTIVE_CHANNEL) {
            Single.just(Pair(channelStatistics, getMostActiveChannelsList(channelList, channelStatistics)))
        } else {
            //we should sort channels list if current filter option is different than most active channel
            Observable.fromIterable(channelList)
                    .toSortedList(ChannelsComparator.getChannelsComparatorForFilterOption(ChannelsFilterOption.MOST_ACTIVE_CHANNEL))
                    .doOnSuccess(this::updateChannelPositionInList)
                    .map { getSelectedChannelFromUpdatedList(channelStatistics.channelId, it) }
                    .doOnSuccess { Timber.d("Most active channels: ${it.second}") }
        }
    }

    private fun getSelectedChannelFromUpdatedList(channelId: String, channelList: List<ChannelStatistics>
    ): Pair<ChannelStatistics, List<ChannelStatistics>> {
        channelList.filter { it.channelId == channelId }
                .forEach { return Pair(it, getMostActiveChannelsList(channelList, it)) }

        throw IllegalStateException("There is no channel with id: $channelId in channels list")
    }

    private fun getMostActiveChannelsList(channelList: List<ChannelStatistics>, channelStatistics: ChannelStatistics): List<ChannelStatistics> {
        val lastMostActiveChannel = channelList[MOST_ACTIVE_CHANNEL_NUMBER - 1]
        if (channelStatistics.currentPositionInList > lastMostActiveChannel.currentPositionInList) {
            //if our channel current position is higher than last in list then just take proper sublist
            return channelList.take(MOST_ACTIVE_CHANNEL_NUMBER)
        } else {
            val mostActiveChannels = channelList.take(MOST_ACTIVE_CHANNEL_NUMBER).toMutableList()
            //selected channel can be inside most active channels list, but it's position can be wrong
            //so we have to remove it from list
            removeSelectedChannelFromChannelList(channelStatistics.channelId, mostActiveChannels)
            // and then add to proper place
            addSelectedChannelToProperPlace(channelStatistics, mostActiveChannels)

            //if we don't remove anything from list, it can contains more than MOST_ACTIVE_CHANNEL_NUMBER
            return mostActiveChannels.take(MOST_ACTIVE_CHANNEL_NUMBER).toList()
        }
    }

    private fun removeSelectedChannelFromChannelList(channelId: String, channelList: MutableList<ChannelStatistics>) {
        val channel = channelList.find { it.channelId == channelId }
        channelList.remove(channel)
    }

    private fun addSelectedChannelToProperPlace(channelStatistics: ChannelStatistics, channelList: MutableList<ChannelStatistics>) {
        for (i in 0.until(channelList.size)) {
            if (channelList[i].currentPositionInList >= channelStatistics.currentPositionInList) {
                channelList.add(i, channelStatistics)
                break
            }
        }
    }

    private fun sortChannelsList(channelList: List<ChannelStatistics>, channelsFilterOption: ChannelsFilterOption): Single<Pair<List<ChannelStatistics>, ChannelsFilterOption>> {
        return Observable.fromIterable(channelList)
                .toSortedList(ChannelsComparator.getChannelsComparatorForFilterOption(channelsFilterOption))
                .doOnSuccess(this::updateChannelPositionInList)
                .map { Pair(it, channelsFilterOption) }
    }

    //TODO 13.07.2017 Should be changed, when there will
    //TODO possibility to obtain all needed information about the channel from SLACK API. Position should be
    //TODO updated according to current position in the sorted list
    private fun updateChannelPositionInList(channelList: List<ChannelStatistics>) {
        if (channelList.isEmpty()) {
            return
        }
        var currentPositionInList = 1
        channelList[0].currentPositionInList = currentPositionInList
        for (i in 1..channelList.size - 1) {
            if (channelList[i].messageCount == channelList[i - 1].messageCount) {
                channelList[i].currentPositionInList = currentPositionInList
            } else {
                channelList[i].currentPositionInList = ++currentPositionInList
            }
        }
    }
}