package co.netguru.android.atstats.feature.channels.profile

import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface ChannelProfileContract {

    interface View : MvpView {

        fun initView(channelStatisticsList: List<ChannelStatistics>, filterOption: ChannelsFilterOption)

        fun scrollToUserPosition(position: Int)

        fun showError()

        fun showShareDialogFragment(channelStatistics: ChannelStatistics, channelList: List<ChannelStatistics>)

        fun showLoadingView()

        fun hideLoadingView()

        fun showSearchView()
    }

    interface Presenter : MvpPresenter<View> {

        fun prepareView(channelStatisticsList: List<ChannelStatistics>, channelPosition: Int, channelsFilter: ChannelsFilterOption)

        fun onShareButtonClick(clickedItemPosition: Int, channelList: List<ChannelStatistics>)

        fun searchButtonClicked()
    }
}