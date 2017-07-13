package co.netguru.android.socialslack.feature.filter

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface FilterContract {

    interface View : MvpView

    interface Presenter : MvpPresenter<View>
}