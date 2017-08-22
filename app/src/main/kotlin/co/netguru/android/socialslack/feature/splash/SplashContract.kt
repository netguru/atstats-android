package co.netguru.android.socialslack.feature.splash

import co.netguru.android.socialslack.common.customTheme.CustomThemeContract
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

interface SplashContract {

    interface View : CustomThemeContract.View {
        fun showMainActivity()

        fun showLoginActivity()
    }

    interface Presenter : CustomThemeContract.Presenter<View>
}