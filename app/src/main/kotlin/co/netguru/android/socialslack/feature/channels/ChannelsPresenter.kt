package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsProvider
import co.netguru.android.socialslack.data.channels.model.Channel
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
class ChannelsPresenter @Inject constructor(private val channelsProvider: ChannelsProvider,
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

    override fun getChannelsFromServer() {
        view.showLoadingView()
        compositeDisposable += channelsProvider.getChannelsList()
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

    override fun sortRequestReceived(channelList: List<Channel>) {
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

    override fun onChannelClick(channel: Channel, channelList: List<Channel>) {
        compositeDisposable +=
                filterController.getChannelsFilterOption()
                        .flatMap { getProperStreamForCurrentFilterOption(it, channel, channelList) }
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
                                                      channel: Channel, channelList: List<Channel>
    ): Single<Pair<Channel, List<Channel>>> {

        return if (currentFilterOption == ChannelsFilterOption.MOST_ACTIVE_CHANNEL) {
            Single.just(Pair(channel, getMostActiveChannelsList(channelList, channel)))
        } else {
            //we should sort channels list if current filter option is different than most active channel
            Observable.fromIterable(channelList)
                    .toSortedList(ChannelsComparator.getChannelsComparatorForFilterOption(ChannelsFilterOption.MOST_ACTIVE_CHANNEL))
                    .doOnSuccess(this::updateChannelPositionInList)
                    .map { getSelectedChannelFromUpdatedList(channel.id, it) }
                    .doOnSuccess { Timber.d("Most active channels: ${it.second}") }
        }
    }

    private fun getSelectedChannelFromUpdatedList(channelId: String, channelList: List<Channel>
    ): Pair<Channel, List<Channel>> {
        channelList.filter { it.id == channelId }
                .forEach { return Pair(it, getMostActiveChannelsList(channelList, it)) }

        throw IllegalStateException("There is no channel with id: $channelId in channels list")
    }

    private fun getMostActiveChannelsList(channelList: List<Channel>, channel: Channel): List<Channel> {
        val lastMostActiveChannel = channelList[MOST_ACTIVE_CHANNEL_NUMBER - 1]
        if (channel.currentPositionInList > lastMostActiveChannel.currentPositionInList) {
            //if our channel current position is higher than last in list then just take proper sublist
            return channelList.take(MOST_ACTIVE_CHANNEL_NUMBER)
        } else {
            val mostActiveChannels = channelList.take(MOST_ACTIVE_CHANNEL_NUMBER).toMutableList()
            //selected channel can be inside most active channels list, but it's position can be wrong
            //so we have to remove it from list
            removeSelectedChannelFromChannelList(channel.id, mostActiveChannels)
            // and then add to proper place
            addSelectedChannelToProperPlace(channel, mostActiveChannels)

            //if we don't remove anything from list, it can contains more than MOST_ACTIVE_CHANNEL_NUMBER
            return mostActiveChannels.take(MOST_ACTIVE_CHANNEL_NUMBER).toList()
        }
    }

    private fun removeSelectedChannelFromChannelList(channelId: String, channelList: MutableList<Channel>) {
        val channel = channelList.find { it.id == channelId }
        channelList.remove(channel)
    }

    private fun addSelectedChannelToProperPlace(channel: Channel, channelList: MutableList<Channel>) {
        for (i in 0.until(channelList.size)) {
            if (channelList[i].currentPositionInList >= channel.currentPositionInList) {
                channelList.add(i, channel)
                break
            }
        }
    }

    private fun sortChannelsList(channelList: List<Channel>, channelsFilterOption: ChannelsFilterOption): Single<Pair<List<Channel>, ChannelsFilterOption>> {
        return Observable.fromIterable(channelList)
                .toSortedList(ChannelsComparator.getChannelsComparatorForFilterOption(channelsFilterOption))
                .doOnSuccess(this::updateChannelPositionInList)
                .map { Pair(it, channelsFilterOption) }
    }

    //TODO 13.07.2017 Should be changed, when there will
    //TODO possibility to obtain all needed information about the channel from SLACK API. Position should be
    //TODO updated according to current position in the sorted list
    private fun updateChannelPositionInList(channelList: List<Channel>) {
        if (channelList.isEmpty()) {
            return
        }
        var currentPositionInList = 1
        channelList[0].currentPositionInList = currentPositionInList
        for (i in 1..channelList.size - 1) {
            if (channelList[i].membersNumber == channelList[i - 1].membersNumber) {
                channelList[i].currentPositionInList = currentPositionInList
            } else {
                channelList[i].currentPositionInList = ++currentPositionInList
            }
        }
    }
}