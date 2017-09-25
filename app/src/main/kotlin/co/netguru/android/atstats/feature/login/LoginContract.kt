package co.netguru.android.atstats.feature.login

import android.net.Uri
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface LoginContract {

    interface View : MvpView {
        fun showOAuthBrowser(uri: Uri)

        fun showMainActivity()

        fun enableLoginButton()

        fun disableLoginButton()

        fun showErrorMessage()
    }

    interface Presenter : MvpPresenter<View> {

        fun loginButtonClicked()

        fun onAppAuthorizeCodeReceived(uri: Uri)
    }
}
