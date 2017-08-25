package co.netguru.android.socialslack.feature.home.homedashboard

import co.netguru.android.socialslack.RxSchedulersOverrideRule
import co.netguru.android.socialslack.feature.home.dashboard.HomeDashboardPresenter
import org.junit.Rule

@Suppress("IllegalIdentifier")
class HomeDashboardPresenterTest {

    @Rule
    @JvmField
    val overrideSchedulersRule = RxSchedulersOverrideRule()

    lateinit var presenter: HomeDashboardPresenter

}