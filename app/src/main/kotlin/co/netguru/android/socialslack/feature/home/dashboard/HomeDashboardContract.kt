package co.netguru.android.socialslack.feature.home.dashboard

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface HomeDashboardContract {

    interface View: MvpView

    interface Presenter: MvpPresenter<View>
}