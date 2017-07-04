package co.netguru.android.socialslack.feature.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.netguru.android.socialslack.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var fragment = supportFragmentManager.findFragmentById(R.id.login_fragment_container)

        if (fragment == null) {
            fragment = LoginFragment.newInstance()

            supportFragmentManager.beginTransaction()
                    .replace(R.id.login_fragment_container, fragment)
                    .commit()
        }
    }
}
