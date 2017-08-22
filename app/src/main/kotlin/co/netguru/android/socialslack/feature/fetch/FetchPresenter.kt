package co.netguru.android.socialslack.feature.fetch

import co.netguru.android.socialslack.app.scope.ActivityScope
import co.netguru.android.socialslack.common.customTheme.CustomThemePresenter
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsController
import co.netguru.android.socialslack.data.direct.DirectChannelsController
import co.netguru.android.socialslack.data.theme.ThemeController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class FetchPresenter @Inject constructor(private val channelsController: ChannelsController,
                                         private val directChannelsController: DirectChannelsController,
                                         themeController: ThemeController)
    : CustomThemePresenter<FetchContract.View>(themeController), FetchContract.Presenter {

    companion object {
        // TODO 11.08.2017 replace this with the user ID
        private const val MOCK_USER = "U2JHH3HAA"
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: FetchContract.View) {
        super.attachView(view)
        compositeDisposable += fetchAndStoreChannelsStatistics()
                .concatWith(fetchAndStoreDirectChannelsStatistics())
                .subscribeBy(
                        onComplete = { view.showMainActivity() },
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

    private fun fetchAndStoreChannelsStatistics() = channelsController.getChannelsList()
            .flattenAsFlowable { it.filter { it.isCurrentUserMember } }
            .flatMapCompletable {
                channelsController.countChannelStatistics(it.id, it.name, MOCK_USER)
                        .toCompletable()
            }
            .compose(RxTransformers.applyCompletableIoSchedulers())

    private fun fetchAndStoreDirectChannelsStatistics() = directChannelsController.getDirectChannelsList()
            .flattenAsFlowable { it }
            .flatMapCompletable {
                directChannelsController.countDirectChannelStatistics(it.id, it.userId)
                        .toCompletable()
            }
            .compose(RxTransformers.applyCompletableIoSchedulers())
}