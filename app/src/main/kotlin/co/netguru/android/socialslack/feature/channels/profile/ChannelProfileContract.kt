package co.netguru.android.socialslack.feature.channels.profile

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ChannelProfile {

    interface View : MvpView {

        fun showChannelInfo(messageNumber: Int, here: String, mentions: String)

        fun showError()

        fun showShareDialogFragment()
    }

    interface Presenter : MvpPresenter<View> {

        fun getChannelInfo()

        fun onShareButtonClick()
    }
}