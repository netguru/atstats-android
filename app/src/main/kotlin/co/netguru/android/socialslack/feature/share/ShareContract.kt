package co.netguru.android.socialslack.feature.share

import co.netguru.android.socialslack.data.channels.model.Channel
import co.netguru.android.socialslack.data.channels.model.ChannelStatistics
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ShareContract {

    interface View : MvpView {

        fun initShareChannelView(selectedChannel: ChannelStatistics, mostActiveChannels: List<ChannelStatistics>)

        fun initShareUserView()

        fun showChannelName(channelName: String)

        fun showShareConfirmationDialog(itemName: String)

        fun showSelectedChannelMostActiveText()

        fun showSelectedChannelTalkMoreText()

        fun showSelectedChannelOnLastPosition(channelStatistics: ChannelStatistics)

        fun showLoadingView()

        fun hideLoadingView()

        fun dismissView()

        fun showError()
    }

    interface Presenter : MvpPresenter<View> {

        fun <T> prepareView(selectedItem: T, mostActiveItemList: List<T>)

        fun onSendButtonClick(screenShotByteArray: ByteArray)

        fun onCloseButtonClick()
    }
}