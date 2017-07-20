package co.netguru.android.socialslack.feature.channels.profile

import co.netguru.android.socialslack.data.channels.model.Channel
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ChannelProfile {

    interface View : MvpView {

        fun showChannelInfo(channel: Channel, messageNumber: Int, here: String, mentions: String)

        fun showError()
    }

    interface Presenter : MvpPresenter<View> {

        fun getChannelInfo()
    }
}