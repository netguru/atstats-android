package co.netguru.android.socialslack.feature.login

import android.content.Intent
import android.os.Bundle
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.common.customTheme.CustomThemeActivity
import co.netguru.android.socialslack.common.customTheme.CustomThemeContract

class LoginActivity : CustomThemeActivity(), CustomThemeContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val fragment = supportFragmentManager.findFragmentById(R.id.login_fragment_container)
                ?: LoginFragment.newInstance()

        supportFragmentManager.beginTransaction()
                .replace(R.id.login_fragment_container, fragment)
                .commit()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val loginFragment = supportFragmentManager
                .findFragmentById(R.id.login_fragment_container) as LoginFragment
        loginFragment.onAppAuthorizeCodeReceived(intent!!.data)
    }
}
