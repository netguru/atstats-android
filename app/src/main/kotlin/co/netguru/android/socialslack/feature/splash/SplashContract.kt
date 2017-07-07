package co.netguru.android.socialslack.feature.splash

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface SplashContract {

    interface View : MvpView {
        fun showMainActivity()

        fun showLoginActivity()
    }

    interface Presenter : MvpPresenter<View>
}