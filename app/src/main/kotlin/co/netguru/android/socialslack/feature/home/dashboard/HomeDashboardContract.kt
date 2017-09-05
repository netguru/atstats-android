package co.netguru.android.socialslack.feature.home.dashboard

import co.netguru.android.socialslack.feature.home.dashboard.model.ChannelsCount
import co.netguru.android.socialslack.feature.home.dashboard.model.DirectChannelsCount
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface HomeDashboardContract {

    interface View : MvpView {

        fun showProfile(username: String?, avatarUrl: String?)

        fun showCounts(channelsCount: ChannelsCount, directChannelsCount: DirectChannelsCount)

        fun showProfileError()

        fun showCountError()
    }

    interface Presenter : MvpPresenter<View>
}