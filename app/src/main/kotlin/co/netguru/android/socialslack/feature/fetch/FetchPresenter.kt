package co.netguru.android.socialslack.feature.fetch

import co.netguru.android.socialslack.app.scope.ActivityScope
import co.netguru.android.socialslack.common.customTheme.CustomThemePresenter
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.channels.ChannelsController
import co.netguru.android.socialslack.data.direct.DirectChannelsController
import co.netguru.android.socialslack.data.session.SessionController
import co.netguru.android.socialslack.data.team.TeamController
import co.netguru.android.socialslack.data.theme.ThemeController
import co.netguru.android.socialslack.data.user.UsersController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class FetchPresenter @Inject constructor(private val sessionController: SessionController,
                                         private val usersController: UsersController,
                                         private val channelsController: ChannelsController,
                                         private val directChannelsController: DirectChannelsController,
                                         private val teamController: TeamController,
                                         themeController: ThemeController)
    : CustomThemePresenter<FetchContract.View>(themeController), FetchContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: FetchContract.View) {
        super.attachView(view)
        fetchAndStoreStatistics()
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun onRefreshButtonClick() {
        view.showLoadingView()
        fetchAndStoreStatistics()
    }

    private fun fetchAndStoreStatistics() {
        compositeDisposable += fetchAndStoreChannelsStatistics()
                .mergeWith(fetchAndStoreOwnUserInfo())
                .mergeWith(fetchAndStoreDirectChannelsStatistics())
                .mergeWith(fetchAndStoreTeam())
                .subscribeBy(
                        onComplete = { view.showMainActivity() },
                        onError = { handleError(it, "Error while fetching data") }
                )
    }

    private fun handleError(throwable: Throwable, message: String) {
        Timber.e(throwable, message)
        view.showError()
    }

    private fun fetchAndStoreOwnUserInfo() = sessionController.getUserSession()
            .flatMap { usersController.getUserAndStore(it.userId) }
            .toCompletable()
            .compose(RxTransformers.applyCompletableIoSchedulers())

    private fun fetchAndStoreChannelsStatistics() = channelsController.getChannelsList()
            .flattenAsFlowable { it.filter { it.isCurrentUserMember } }
            .filter { !it.isArchived }
            .flatMapCompletable { (id, name) ->
                sessionController.getUserSession().flatMapCompletable {
                    channelsController.countChannelStatistics(id, name, it.userId)
                            .toCompletable()
                            .subscribeOn(Schedulers.io())
                }
            }
            .compose(RxTransformers.applyCompletableIoSchedulers())

    private fun fetchAndStoreDirectChannelsStatistics() = directChannelsController.getDirectChannelsList()
            .flattenAsFlowable { it }
            .filter { !it.isUserDeleted }
            .flatMapCompletable { (id, userId) ->
                sessionController.getUserSession().flatMapCompletable {
                    directChannelsController.countDirectChannelStatistics(id, userId, it.userId == userId)
                            .toCompletable()
                            .subscribeOn(Schedulers.io())
                }
            }
            .compose(RxTransformers.applyCompletableIoSchedulers())

    private fun fetchAndStoreTeam() = teamController.fetchTeamInfo()
            .compose(RxTransformers.applyCompletableIoSchedulers())
}