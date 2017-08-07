package co.netguru.android.socialslack.feature.splash

import android.os.Bundle
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.startActivity
import co.netguru.android.socialslack.feature.fetch.FetchActivity
import co.netguru.android.socialslack.feature.login.LoginActivity
import com.hannesdorfmann.mosby3.mvp.MvpActivity


class SplashActivity : MvpActivity<SplashContract.View, SplashContract.Presenter>(), SplashContract.View {

    private lateinit var component: SplashComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        initComponent()
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

    private fun initComponent() {
        component = App.getApplicationComponent(this)
                .plusSplashComponent()
    }
}