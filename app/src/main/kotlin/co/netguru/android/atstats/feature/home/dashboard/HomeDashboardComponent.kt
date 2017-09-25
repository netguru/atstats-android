package co.netguru.android.atstats.feature.home.dashboard

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface HomeDashboardComponent {

    fun getPresenter(): HomeDashboardPresenter
}