package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsController
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
class ChannelsPresenter @Inject constructor(private val channelsController: ChannelsController,
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
        compositeDisposable += channelsController.getChannelsList()
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