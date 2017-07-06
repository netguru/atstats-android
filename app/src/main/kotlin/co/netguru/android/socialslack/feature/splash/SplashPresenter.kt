package co.netguru.android.socialslack.feature.splash

import co.netguru.android.socialslack.app.scope.ActivityScope
import co.netguru.android.socialslack.data.session.TokenController
import co.netguru.android.socialslack.data.session.model.TokenCheck
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class SplashPresenter @Inject constructor(private val tokenController: TokenController) : MvpNullObjectBasePresenter<SplashContract.View>(),
        SplashContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: SplashContract.View) {
        super.attachView(view)
        compositeDisposable.add(
                tokenController.isTokenValid()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onCheckTokenNext, this::handleError))
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