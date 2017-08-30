package co.netguru.android.socialslack.feature.search

import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.user.model.UserStatistic
import co.netguru.android.socialslack.feature.search.adapter.SearchItemType
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface SearchContract {

    interface View : MvpView {

        fun initChannelSearchView(channelsList : List<ChannelStatistics>)

        fun initUsersSearchView(usersList : List<UserStatistic>)

        fun showProgressBar()

        fun hideProgressBar()

        fun showError()
    }

    interface Presenter : MvpPresenter<View> {

        fun searchItemReceived(searchItemType: SearchItemType)
    }
}