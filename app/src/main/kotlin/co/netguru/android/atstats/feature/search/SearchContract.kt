package co.netguru.android.atstats.feature.search

import co.netguru.android.atstats.data.channels.model.ChannelStatistics
import co.netguru.android.atstats.data.user.model.User
import co.netguru.android.atstats.feature.search.adapter.SearchItemType
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface SearchContract {

    interface View : MvpView {

        fun initChannelSearchView(channelsList: List<ChannelStatistics>)

        fun initUsersSearchView(usersList: List<User>)

        fun filterChannelsList(query: String)

        fun filterUsersList(query: String)

        fun showProgressBar()

        fun hideProgressBar()

        fun showError()
    }

    interface Presenter : MvpPresenter<View> {

        fun searchItemReceived(searchItemType: SearchItemType)

        fun searchQueryReceived(query: String)
    }
}