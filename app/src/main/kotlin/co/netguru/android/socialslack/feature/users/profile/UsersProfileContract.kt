package co.netguru.android.socialslack.feature.users.profile

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface UsersProfileContract {

    interface View : MvpView

    interface Presenter : MvpPresenter<View>
}