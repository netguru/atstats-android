package co.netguru.android.atstats.feature.splash

import co.netguru.android.atstats.common.customTheme.CustomThemeContract

interface SplashContract {

    interface View : CustomThemeContract.View {
        fun showFetchActivity()

        fun showLoginActivity()
    }

    interface Presenter : CustomThemeContract.Presenter<View>
}