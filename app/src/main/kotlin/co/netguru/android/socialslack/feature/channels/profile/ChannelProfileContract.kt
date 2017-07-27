package co.netguru.android.socialslack.feature.channels.profile

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface ChannelProfileContract {

    interface View: MvpView {

        fun showChannelInfo(totalHere: Int, totalMentions: Int)

        fun showError()
    }

    interface Presenter: MvpPresenter<View> {

        fun getChannelInfo(ChannelId: String)
    }
}