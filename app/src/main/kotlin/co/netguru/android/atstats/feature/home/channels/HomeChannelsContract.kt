package co.netguru.android.atstats.feature.home.channels

import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface HomeChannelsContract {

    interface View : MvpView {

        fun showMostActiveChannels(mostActiveChannelList: List<ChannelStatistics>, filter: ChannelsFilterOption)

        fun showChannelsWeAreMentionTheMost(weAreMentionMostChannelList: List<ChannelStatistics>, filter: ChannelsFilterOption)

        fun showChannelsWeAreMostActive(weAreMostActiveChannelList: List<ChannelStatistics>, filter: ChannelsFilterOption)

        fun showErrorSortingChannels()
    }

    interface Presenter : MvpPresenter<View>
}