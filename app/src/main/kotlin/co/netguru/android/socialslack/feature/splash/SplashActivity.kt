package co.netguru.android.socialslack.feature.splash

import android.os.Bundle
import co.netguru.android.socialslack.common.extensions.startActivity
import co.netguru.android.socialslack.feature.login.LoginActivity
import co.netguru.android.socialslack.feature.main.MainActivity
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import co.netguru.android.socialslack.app.App



class SplashActivity : MvpActivity<SplashContract.View, SplashContract.Presenter>(), SplashContract.View {

    private lateinit var component: SplashComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        initComponent()
        super.onCreate(savedInstanceState)
    }

    override fun createPresenter(): SplashContract.Presenter = component.getPresenter()

    override fun showMainActivity() {
        startActivity<MainActivity>()
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