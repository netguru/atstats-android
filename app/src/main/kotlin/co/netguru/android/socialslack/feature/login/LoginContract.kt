package co.netguru.android.socialslack.feature.login

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface LoginContract {

    interface View : MvpView {

    }

    interface Presenter : MvpPresenter<View> {

    }
}
