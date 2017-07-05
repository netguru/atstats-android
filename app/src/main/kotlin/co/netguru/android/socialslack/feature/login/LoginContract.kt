package co.netguru.android.socialslack.feature.login

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface LoginContract {

    interface View : MvpView {
        fun showOathWebView(uri : String)
    }

    interface Presenter : MvpPresenter<View> {

        fun loginButtonClicked()
    }
}
