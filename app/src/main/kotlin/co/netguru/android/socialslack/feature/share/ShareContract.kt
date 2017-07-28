package co.netguru.android.socialslack.feature.share

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface ShareContract {

    interface View : MvpView {

        fun showShareConfirmationDialog()
    }

    interface Presenter : MvpPresenter<View> {

        fun onSendButtonClick()
    }
}