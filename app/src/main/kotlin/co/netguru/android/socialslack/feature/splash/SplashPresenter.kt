package co.netguru.android.socialslack.feature.splash

import co.netguru.android.socialslack.app.scope.ActivityScope
import co.netguru.android.socialslack.common.customTheme.CustomThemePresenter
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.session.SessionController
import co.netguru.android.socialslack.data.session.model.TokenCheck
import co.netguru.android.socialslack.data.session.model.UserSession
import co.netguru.android.socialslack.data.theme.ThemeController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class SplashPresenter @Inject constructor(private val sessionController: SessionController,
                                          themeController: ThemeController)
    : CustomThemePresenter<SplashContract.View>(themeController), SplashContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: SplashContract.View) {
        super.attachView(view)
        compositeDisposable += sessionController.isTokenValid()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = this::onCheckTokenNext,
                        onError = this::handleError
                )
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    private fun onCheckTokenNext(tokenCheck: TokenCheck) {
        when (tokenCheck.isTokenValid) {
            true -> view.showMainActivity()
            false -> view.showLoginActivity()
        }
    }

    private fun handleError(throwable: Throwable) {
        Timber.e(throwable, "Error while checking token")
        view.showLoginActivity()
    }
}