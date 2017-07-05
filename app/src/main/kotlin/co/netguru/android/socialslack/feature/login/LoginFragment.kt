package co.netguru.android.socialslack.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : MvpFragment<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    private lateinit var component: LoginComponent

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initComponent()
        return inflater?.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_sigin_btn.setOnClickListener { getPresenter().loginButtonClicked() }
    }

    override fun createPresenter(): LoginContract.Presenter  = component.getPresenter()

    private fun initComponent() {
        component = App.Companion.getApplicationComponent(context)
                .plusLoginComponent()
        component.inject(this)
    }
}
