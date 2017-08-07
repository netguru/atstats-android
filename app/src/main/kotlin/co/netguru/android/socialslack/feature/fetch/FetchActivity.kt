package co.netguru.android.socialslack.feature.fetch

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.common.extensions.startActivity
import co.netguru.android.socialslack.feature.main.MainActivity
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_fetch.*


class FetchActivity : MvpActivity<FetchContract.View, FetchContract.Presenter>(), FetchContract.View {

    private val component by lazy { App.getApplicationComponent(this) .plusFetchComponent() }

    override fun showMainActivity() {
        startActivity<MainActivity>()
        finish()
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