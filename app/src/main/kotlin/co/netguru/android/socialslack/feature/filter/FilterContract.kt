package co.netguru.android.socialslack.feature.filter

import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.filter.model.FilterObjectType
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface FilterContract {

    interface View : MvpView {
        fun initChannelsFilterFragment()

        fun selectCurrentChannelFilter(currentChannelsFilterOption: ChannelsFilterOption)
    }

    interface Presenter : MvpPresenter<View> {

        fun filterObjectTypeReceived(filterObjectType: FilterObjectType)

        fun filterOptionChanged(channelsFilterOption: ChannelsFilterOption)
    }
}