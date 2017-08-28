package co.netguru.android.socialslack.feature.login

import android.net.Uri
import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.common.util.RxTransformers
import co.netguru.android.socialslack.data.session.SessionController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class LoginPresenter @Inject constructor(private val sessionController: SessionController)
    : MvpNullObjectBasePresenter<LoginContract.View>(), LoginContract.Presenter {

    companion object {
        private const val SLACK_API_CODE = "code"
    }

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun loginButtonClicked() {
        compositeDisposable += sessionController.getOauthAuthorizeUri()
                .compose(RxTransformers.applySingleIoSchedulers())
                .subscribeBy(
                        onSuccess = {
                            view.showOAuthBrowser(it)
                            view.disableLoginButton()
                        },
                        onError = { handleError(it, "Error while getting OAuth URI") }
                )
    }

    override fun onAppAuthorizeCodeReceived(uri: Uri) {
        compositeDisposable += sessionController.requestNewToken(getCodeFromUri(uri))
                .flatMapCompletable(sessionController::saveToken)
                .concatWith(sessionController.checkToken().toCompletable())
                .compose(RxTransformers.applyCompletableIoSchedulers())
                .subscribeBy(
                        onComplete = view::showMainActivity,
                        onError = { handleError(it, "Error while requesting new token") }
                )
    }

    private fun getCodeFromUri(uri: Uri): String = uri.getQueryParameter(SLACK_API_CODE)

    private fun handleError(throwable: Throwable, message: String) {
        Timber.e(throwable, message)
        view.showErrorMessage()
        view.enableLoginButton()
    }
}
