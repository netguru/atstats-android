package co.netguru.android.socialslack.feature.splash

import android.os.Bundle
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.customTheme.MvpCustomThemeActivity
import co.netguru.android.socialslack.common.extensions.startActivity
import co.netguru.android.socialslack.feature.fetch.FetchActivity
import co.netguru.android.socialslack.feature.login.LoginActivity


class SplashActivity : MvpCustomThemeActivity<SplashContract.View, SplashContract.Presenter>(), SplashContract.View {

    val component by lazy { App.getApplicationComponent(this).plusSplashComponent() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun createPresenter(): SplashContract.Presenter = component.getPresenter()

    override fun showMainActivity() {
        startActivity<FetchActivity>()
        finish()
    }

    override fun showLoginActivity() {
        startActivity<LoginActivity>()
        finish()
    }

    override fun inject() {
        component.inject(this)
    }
}