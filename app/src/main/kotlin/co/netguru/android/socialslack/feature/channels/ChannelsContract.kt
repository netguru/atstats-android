package co.netguru.android.socialslack.feature.channels

import android.support.annotation.StringRes
import co.netguru.android.socialslack.data.channels.model.Channel
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ChannelsContract {

    interface View : MvpView {
        fun showChannels(channelList: List<Channel>)

        fun showError()

        fun showFilterOptionError()

        fun showFilterView()

        fun setCurrentFilterOptionText(@StringRes stringResId: Int)

        fun showLoadingView()

        fun hideLoadingView()

        fun showChannelDetails(channel: Channel, channelList: List<Channel>)
    }

    interface Presenter : MvpPresenter<View> {
        fun getCurrentFilterOption()

        fun getChannelsFromServer()

        fun filterButtonClicked()

        fun onChannelClick(channel: Channel, channelList: List<Channel>)

        fun sortRequestReceived(channelList: List<Channel>)
    }
}