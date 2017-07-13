package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.data.channels.model.Channel
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ChannelsContract {

    interface View : MvpView {
        fun showChannels(channelList: List<Channel>)

        fun showError()

        fun showFilterView()
    }

    interface Presenter : MvpPresenter<View> {
        fun getChannelsFromServer()

        fun filterButtonClicked()
    }
}