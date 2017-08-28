package co.netguru.android.socialslack.feature.channels.profile

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface ChannelProfileContract {

    interface View : MvpView {

        fun showChannelInfo(totalMessages: Int, totalHere: Int, totalMentions: Int)

        fun showError()

        fun showShareDialogFragment()

        fun showLoadingView()

        fun hideLoadingView()

        fun showSearchView()
    }

    interface Presenter : MvpPresenter<View> {

        fun getChannelInfo(ChannelId: String)

        fun onShareButtonClick()

        fun searchButtonClicked()
    }
}