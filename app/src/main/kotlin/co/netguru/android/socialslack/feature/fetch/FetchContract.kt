package co.netguru.android.socialslack.feature.fetch

import co.netguru.android.socialslack.common.customTheme.CustomThemeContract
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface FetchContract {

    interface View: CustomThemeContract.View {

        fun showMainActivity()

        fun showError()

        fun showLoadingView()
    }

    interface Presenter: CustomThemeContract.Presenter<View> {
        fun onRefreshButtonClick()
    }
}