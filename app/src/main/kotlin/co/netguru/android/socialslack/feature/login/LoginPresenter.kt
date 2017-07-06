package co.netguru.android.socialslack.feature.login

import android.net.Uri
import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.data.session.TokenController
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription
import timber.log.Timber
import javax.inject.Inject

@FragmentScope
class LoginPresenter @Inject constructor(val tokenController: TokenController)
    : MvpNullObjectBasePresenter<LoginContract.View>(), LoginContract.Presenter {

    private val compositeSubscription = CompositeSubscription()

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        compositeSubscription.clear()
    }

    override fun loginButtonClicked() {
        compositeSubscription.add(
                tokenController.getOauthAuthorizeUri()
                        .subscribeOn(Schedulers.io())
                        .subscribe(view::showOAuthBrowser
                        ) { Timber.e(it, "Error while getting OAuth URI") })
    }

    override fun onAppAuthorizeCodeReceived(uri: Uri) {
        compositeSubscription.add(
                tokenController.requestNewToken(getCodeFromUri(uri))
                        .flatMapCompletable(tokenController::saveToken)
                        .subscribeOn(Schedulers.io())
                        .subscribe(view::showMainActivity)
                        { Timber.e(it, "Error while requesting new Token") }
        )
    }

    private fun getCodeFromUri(uri: Uri): String = uri.getQueryParameter(SLACK_API_CODE)

    companion object {
        private const val SLACK_API_CODE = "code"
    }
}
