package co.netguru.android.socialslack.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_login.*
import android.content.Intent
import android.net.Uri
import android.support.design.widget.Snackbar
import co.netguru.android.socialslack.common.extensions.inflate
import co.netguru.android.socialslack.feature.main.MainActivity
import co.netguru.android.socialslack.common.extensions.startActivity


class LoginFragment : MvpFragment<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    private lateinit var component: LoginComponent

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initComponent()
        return container?.inflate(R.layout.fragment_login)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginSignInBtn.setOnClickListener { getPresenter().loginButtonClicked() }
    }

    fun onAppAuthorizeCodeReceived(uri: Uri) = presenter.onAppAuthorizeCodeReceived(uri)

    override fun showOAuthBrowser(uri: Uri) {
        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(browserIntent)
    }

    override fun enableLoginButton() {
        loginSignInBtn.isEnabled = true
    }

    override fun disableLoginButton() {
        loginSignInBtn.isEnabled = false
    }

    override fun showErrorMessage() {
        Snackbar.make(loginSignInBtn, R.string.login_error_msg, Snackbar.LENGTH_LONG).show()
    }

    override fun showMainActivity() {
        activity.startActivity<MainActivity>()
        activity.finish()
    }

    override fun createPresenter(): LoginContract.Presenter = component.getPresenter()

    private fun initComponent() {
        component = App.Factory.getApplicationComponent(context)
                .plusLoginComponent()
        component.inject(this)
    }
}
