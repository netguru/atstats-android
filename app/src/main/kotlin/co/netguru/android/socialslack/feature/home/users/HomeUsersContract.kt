package co.netguru.android.socialslack.feature.home.users

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface HomeUsersContract {

    interface View : MvpView {
    }

    interface Presenter : MvpPresenter<View> {
    }
}