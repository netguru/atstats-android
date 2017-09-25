package co.netguru.android.atstats.feature.fetch

import android.os.Bundle
import android.view.View
import co.netguru.android.atstats.R
import co.netguru.android.atstats.app.App
import co.netguru.android.atstats.common.customTheme.MvpCustomThemeActivity
import co.netguru.android.atstats.common.extensions.startActivity
import co.netguru.android.atstats.data.shared.RandomMessageProvider
import co.netguru.android.atstats.feature.main.MainActivity
import kotlinx.android.synthetic.main.activity_fetch.*


class FetchActivity : MvpCustomThemeActivity<FetchContract.View, FetchContract.Presenter>(), FetchContract.View {

    private val component by lazy { App.getUserComponent(this).plusFetchComponent() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch)
        fetchDescriptionTextView.text = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.loadingMessages))
        fetchRefreshButton.setOnClickListener { presenter.onRefreshButtonClick() }
    }

    override fun createPresenter(): FetchContract.Presenter = component.getPresenter()

    override fun showMainActivity() {
        startActivity<MainActivity>()
        finish()
    }

    override fun showLoadingView() {
        progressBar.visibility = View.VISIBLE
        fetchRefreshButton.visibility = View.GONE
        fetchDescriptionTextView.text = RandomMessageProvider.getRandomMessageFromArray(resources.getStringArray(R.array.loadingMessages))
    }

    override fun showError() {
        progressBar.visibility = View.GONE
        fetchRefreshButton.visibility = View.VISIBLE
        fetchDescriptionTextView.setText(R.string.fragment_fetch_error)
    }
}