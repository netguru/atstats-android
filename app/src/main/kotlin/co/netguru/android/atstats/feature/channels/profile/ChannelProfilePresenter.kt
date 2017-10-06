package co.netguru.android.atstats.feature.channels.profile

import co.netguru.android.atstats.app.scope.FragmentScope
import co.netguru.android.atstats.common.util.RxTransformers
import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import co.netguru.android.atstats.data.share.SharableListProvider
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class ChannelProfilePresenter @Inject constructor() :
        MvpNullObjectBasePresenter<ChannelProfileContract.View>(), ChannelProfileContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun prepareView(channelStatisticsList: List<ChannelStatistics>, channelPosition: Int, channelsFilter: ChannelsFilterOption) {

        view.showLoadingView()

        compositeDisposable += Single.just(channelStatisticsList)
                .subscribeBy(
                        onSuccess = {
                            view.initView(it, channelsFilter)
                            view.scrollToChannelPosition(channelPosition)
                            view.hideLoadingView()
                        },
                        onError = {
                            Timber.e(it, "Couldn't show the channel")
                            view.hideLoadingView()
                            view.showError()
                        }
                )
    }

    override fun onShareButtonClick(clickedItemPosition: Int, channelList: List<ChannelStatistics>) {
        compositeDisposable += Single.just(channelList)
                .map { SharableListProvider.getSharableList(clickedItemPosition, channelList) }
                .map { it.filterIsInstance(ChannelStatistics::class.java) }
                .compose(RxTransformers.applySingleComputationSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.showShareDialogFragment(channelList[clickedItemPosition], it)
                        },
                        onError = {
                            Timber.e(it, "Error while getting sharable channel list")
                        }
                )
    }

    override fun searchButtonClicked() {
        view.showSearchView()
    }
}