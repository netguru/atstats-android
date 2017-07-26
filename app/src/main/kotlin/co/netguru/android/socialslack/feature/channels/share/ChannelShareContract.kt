package co.netguru.android.socialslack.feature.channels.share

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ChannelShareContract {

    interface View : MvpView

    interface Presenter : MvpPresenter<View>
}