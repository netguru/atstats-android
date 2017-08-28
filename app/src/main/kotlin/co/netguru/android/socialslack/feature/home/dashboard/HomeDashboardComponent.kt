package co.netguru.android.socialslack.feature.home.dashboard

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface HomeDashboardComponent {

    fun getPresenter(): HomeDashboardPresenter
}