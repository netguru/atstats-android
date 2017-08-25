package co.netguru.android.socialslack.feature.home.channels

import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface HomeChannelsContract {

    interface View: MvpView {

        fun showMostActiveChannels(mostActiveChannelList: List<ChannelStatistics>)

        fun showChannelsWeAreMentionTheMost(weAreMentionMostChannelList: List<ChannelStatistics>)

        fun showChannelsWeAreMostActive(weAreMostActiveChannelList: List<ChannelStatistics>)

        fun showErrorSortingChannels()
    }

    interface Presenter: MvpPresenter<View>
}