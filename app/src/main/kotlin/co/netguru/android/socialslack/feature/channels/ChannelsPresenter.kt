package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsController
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.filter.FilterController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
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

    override fun getChannelsFromServer() {
        //TODO 11.07.2017 Channels list should be sorted after adding sorting component
        compositeDisposable += channelsController.getChannelsList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = view::showChannels,
                        onError = {
                            Timber.e(it, "Error while getting channels from server")
                            view.showError()
                        })
    }

    override fun filterButtonClicked() {
        view.showFilterView()
    }

    override fun sortRequestReceived(channelList: List<Channel>) {
        compositeDisposable += filterController.getChannelsFilterOption()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            Timber.e("test" + it.value)
                        },
                        onError = {
                            Timber.e(it, "Error while getting sorting channels")
                        })
    }
}