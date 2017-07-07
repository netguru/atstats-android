package co.netguru.android.socialslack.feature.splash

import co.netguru.android.socialslack.app.scope.ActivityScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.session.TokenController
import co.netguru.android.socialslack.data.session.model.TokenCheck
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class SplashPresenter @Inject constructor(private val tokenController: TokenController) : MvpNullObjectBasePresenter<SplashContract.View>(),
        SplashContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: SplashContract.View) {
        super.attachView(view)
        compositeDisposable += tokenController.isTokenValid()
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