package co.netguru.android.socialslack.feature.share

import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import co.netguru.android.socialslack.data.filter.model.ChannelsFilterOption
import co.netguru.android.socialslack.data.filter.model.Filter
import co.netguru.android.socialslack.data.filter.model.UsersFilterOption
import co.netguru.android.socialslack.data.user.model.UserStatistic
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ShareContract {

    interface View : MvpView {

        fun initShareChannelView(channelsList: List<ChannelStatistics>, filterOption: ChannelsFilterOption)

        fun initShareUsersView(userList: List<UserStatistic>, filterOption: UsersFilterOption)

        fun showChannelName(channelName: String)

        fun showShareConfirmationDialog(itemName: String)

        fun showSelectedChannelMostActiveText()

        fun showSelectedChannelTalkMoreText()

        fun showSelectedChannelOnLastPosition(channelStatistics: ChannelStatistics, filterOption: ChannelsFilterOption)

        fun showSelectedUserMostActiveText()

        fun showSelectedUserTalkMoreText()

        fun showSelectedUserOnLastPosition(user: UserStatistic, filterOption: UsersFilterOption)

        fun showLoadingView()

        fun hideLoadingView()

        fun dismissView()

        fun showError()
    }

    interface Presenter : MvpPresenter<View> {

        fun <T : Any> prepareView(selectedItem: T, mostActiveItemList: List<T>, filter: Filter)

        fun onSendButtonClick(screenShotByteArray: ByteArray)

        fun onCloseButtonClick()
    }
}