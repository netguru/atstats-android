package co.netguru.android.atstats.feature.fetch

import co.netguru.android.atstats.common.customTheme.CustomThemeContract


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