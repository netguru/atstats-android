package co.netguru.android.socialslack.feature.home.channels

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface HomeChannelsContract {

    interface View: MvpView

    interface Presenter: MvpPresenter<View>
}