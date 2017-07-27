package co.netguru.android.socialslack.feature.share

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ShareContract {

    interface View : MvpView {

        fun showShareConfirmationDialog()

        fun showLoadingView()

        fun hideLoadingView()

        fun dismissView()

        fun showError()
    }

    interface Presenter : MvpPresenter<View> {

        fun onSendButtonClick(screenShotByteArray: ByteArray)

        fun onCloseButtonClick()
    }
}