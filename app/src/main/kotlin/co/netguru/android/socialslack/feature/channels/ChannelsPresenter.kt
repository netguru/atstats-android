package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class ChannelsPresenter @Inject constructor(private val channelsController: ChannelsController)
    : MvpNullObjectBasePresenter<ChannelsContract.View>(), ChannelsContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun getChannelsFromServer() {
        compositeDisposable += channelsController.getChannelsList()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = view::showChannels,
                        onError = {
                            Timber.e(it, "Error while getting channels from server")
                            view.showError()
                        })
    }
}