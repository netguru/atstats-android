package co.netguru.android.socialslack.feature.profile

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface ProfileContract {

    interface View: MvpView{

    }

    interface Presenter: MvpPresenter<View> {

    }
}