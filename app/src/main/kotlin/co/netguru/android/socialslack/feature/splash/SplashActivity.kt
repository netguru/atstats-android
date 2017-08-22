package co.netguru.android.socialslack.feature.splash

import android.os.Bundle
import android.os.PersistableBundle
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.startActivity
import co.netguru.android.socialslack.feature.fetch.FetchActivity
import co.netguru.android.socialslack.feature.login.LoginActivity
import com.hannesdorfmann.mosby3.mvp.MvpActivity


class SplashActivity : MvpActivity<SplashContract.View, SplashContract.Presenter>(), SplashContract.View {

    val component by lazy { App.getApplicationComponent(this).plusSplashComponent() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme()
        super.onCreate(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        setTheme()
        super.onCreate(savedInstanceState, persistentState)
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

    private fun setTheme() {
        setTheme(if (createPresenter().showColourfulTheme()) R.style.SplashThemeColourful else R.style.SplashThemeNetguru)
    }
}