package co.netguru.android.atstats.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.App
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_login.*
import android.content.Intent
import android.net.Uri
import android.support.design.widget.Snackbar
import co.netguru.android.atstats.common.extensions.inflate
import co.netguru.android.atstats.common.extensions.startActivity
import co.netguru.android.atstats.data.shared.RandomMessageProvider
import co.netguru.android.atstats.feature.fetch.FetchActivity

class LoginFragment : MvpFragment<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    private val component by lazy {
        App.getApplicationComponent(context)
                .plusLoginComponent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) = container?.inflate(R.layout.fragment_login)

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
        val errorMsg = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.errorMessages))
        Snackbar.make(loginSignInBtn, errorMsg, Snackbar.LENGTH_LONG).show()
    }

    override fun showMainActivity() {
        activity.startActivity<FetchActivity>()
        activity.finish()
    }

    override fun createPresenter(): LoginContract.Presenter = component.getPresenter()
}
