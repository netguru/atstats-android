package co.netguru.android.socialslack.feature.fetch

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface FetchContract {

    interface View: MvpView {

        fun showMainActivity()

        fun showErrorMessage()
    }

    interface Presenter: MvpPresenter<View>
}