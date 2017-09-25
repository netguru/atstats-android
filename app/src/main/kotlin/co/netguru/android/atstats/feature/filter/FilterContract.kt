package co.netguru.android.atstats.feature.filter

import co.netguru.android.atstats.data.filter.model.ChannelsFilterOption
import co.netguru.android.atstats.data.filter.model.FilterObjectType
import co.netguru.android.atstats.data.filter.model.UsersFilterOption
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface FilterContract {

    interface View : MvpView {
        fun initChannelsFilterFragment()

        fun selectCurrentChannelFilter(currentChannelsFilterOption: ChannelsFilterOption)

        fun initUsersFilterFragment()

        fun selectCurrentUsersFilter(currentUsersFilterOption: UsersFilterOption)

        fun getCurrentChannelsFilterOption(): ChannelsFilterOption

        fun getCurrentUsersFilterOption(): UsersFilterOption

        fun showMainActivityWithRequestChannelsSort()

        fun showMainActivityWithRequestUsersSort()
    }

    interface Presenter : MvpPresenter<View> {

        fun filterObjectTypeReceived(filterObjectType: FilterObjectType)

        fun filterOptionChanged()
    }
}