package co.netguru.android.socialslack.feature.login

import android.net.Uri
import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.data.session.TokenController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class LoginPresenter @Inject constructor(private val tokenController: TokenController)
    : MvpNullObjectBasePresenter<LoginContract.View>(), LoginContract.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeDisposable.clear()
    }

    override fun loginButtonClicked() {
        compositeDisposable.add(
                tokenController.getOauthAuthorizeUri()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            view.showOAuthBrowser(it)
                            view.disableLoginButton()
                        }) { handleError(it, "Error while getting OAuth URI") })
    }

    override fun onAppAuthorizeCodeReceived(uri: Uri) {
        compositeDisposable.add(
                tokenController.requestNewToken(getCodeFromUri(uri))
                        .flatMapCompletable(tokenController::saveToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view::showMainActivity)
                        { handleError(it, "Error while requesting new token") }
        )
    }

    private fun getCodeFromUri(uri: Uri): String = uri.getQueryParameter(SLACK_API_CODE)

    private fun handleError(throwable: Throwable, message: String) {
        Timber.e(throwable, message)
        view.showErrorMessage()
        view.enableLoginButton()
    }

    companion object {
        private const val SLACK_API_CODE = "code"
    }
}
