package co.netguru.android.socialslack.feature.channels.profile

import co.netguru.android.socialslack.data.channels.model.Channel
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ChannelProfile {

    interface View : MvpView {

        fun showChannelInfo(channelName: String, totalMessages: Int, totalHere: Int, totalMentions: Int)

        fun showError()
    }

    interface Presenter : MvpPresenter<View> {

        fun getChannelInfo(channelId: String)
    }
}