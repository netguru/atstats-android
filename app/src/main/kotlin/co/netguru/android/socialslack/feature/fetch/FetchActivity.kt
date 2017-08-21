package co.netguru.android.socialslack.feature.fetch

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.customTheme.MvpCustomThemeActivity
import co.netguru.android.socialslack.common.extensions.startActivity
import co.netguru.android.socialslack.feature.main.MainActivity
import kotlinx.android.synthetic.main.activity_fetch.*


class FetchActivity : MvpCustomThemeActivity<FetchContract.View, FetchContract.Presenter>(), FetchContract.View {

    private val component by lazy { App.getUserComponent(this).plusFetchComponent() }

    override fun showMainActivity() {
        startActivity<MainActivity>()
        finish()
    }

    override fun inject() {
        component.inject(this)
    }

    override fun showErrorMessage() {
        progressBar.visibility = View.INVISIBLE
        Snackbar.make(mainLayout, R.string.error_msg, Snackbar.LENGTH_LONG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch)
    }

    override fun createPresenter(): FetchContract.Presenter = component.getPresenter()
}