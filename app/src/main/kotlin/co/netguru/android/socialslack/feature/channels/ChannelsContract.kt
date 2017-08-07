package co.netguru.android.socialslack.feature.channels

import android.support.annotation.StringRes
import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ChannelsContract {

    interface View : MvpView {
        fun showChannels(channelList: List<ChannelStatistics>)

        fun showError()

        fun showFilterOptionError()

        fun showFilterView()

        fun setCurrentFilterOptionText(@StringRes stringResId: Int)

        fun showLoadingView()

        fun hideLoadingView()

        fun showChannelDetails(channelStatistics: ChannelStatistics, mostActiveChannelList: List<ChannelStatistics>)
    }

    interface Presenter : MvpPresenter<View> {
        fun getCurrentFilterOption()

        fun getChannels()

        fun filterButtonClicked()

        fun onChannelClick(channelStatistics: ChannelStatistics, channelList: List<ChannelStatistics>)

        fun sortRequestReceived(channelList: List<ChannelStatistics>)
    }
}