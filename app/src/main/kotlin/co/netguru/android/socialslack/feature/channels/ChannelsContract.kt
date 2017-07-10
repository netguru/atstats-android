package co.netguru.android.socialslack.feature.channels

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ChannelsContract {

    interface View : MvpView

    interface Presenter : MvpPresenter<View>
}