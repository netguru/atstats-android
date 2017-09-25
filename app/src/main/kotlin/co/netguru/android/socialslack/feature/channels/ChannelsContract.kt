package co.netguru.android.socialslack.feature.channels

import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ChannelsContract {

    interface View : MvpView {
        fun showChannels(channelList: List<ChannelStatistics>)

        fun showError()

        fun showFilterOptionError()

        fun showFilterView()

        fun showSearchView()

        fun setCurrentFilterOption(filterOption: ChannelsFilterOption)

        fun showLoadingView()

        fun hideLoadingView()

        fun showChannelDetails(channelStatistics: ChannelStatistics, channelList: List<ChannelStatistics>, filterOption: ChannelsFilterOption)
    }

    interface Presenter : MvpPresenter<View> {
        fun getCurrentFilterOption()

        fun getChannels()

        fun filterButtonClicked()

        fun searchButtonClicked()

        fun onChannelClick(selectedItemPosition: Int, channelList: List<ChannelStatistics>)

        fun sortRequestReceived()
    }
}