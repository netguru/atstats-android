package co.netguru.android.socialslack.feature.fetch

import co.netguru.android.socialslack.app.scope.ActivityScope
import co.netguru.android.socialslack.data.channels.ChannelsController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class FetchPresenter @Inject constructor(private val channelsController: ChannelsController) : MvpNullObjectBasePresenter<FetchContract.View>(), FetchContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: FetchContract.View) {
        super.attachView(view)
        compositeDisposable += channelsController.getChannelsList()
                .flattenAsFlowable { it.filter { it.isCurrentUserMember } }
                .flatMapSingle { channelsController.countChannelStatistics(it.id, it.name, "user") }
                .toList()
                .subscribeBy(
                        onSuccess = { view.showMainActivity() },
                        onError = { handleError(it, "Error while fetching data") }
                )

    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    private fun handleError(throwable: Throwable, message: String) {
        Timber.e(throwable, message)
        view.showErrorMessage()
    }
}