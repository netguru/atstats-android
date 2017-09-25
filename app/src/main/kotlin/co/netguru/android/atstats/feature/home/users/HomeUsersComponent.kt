package co.netguru.android.atstats.feature.home.users

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface HomeUsersComponent {

    fun getPresenter() : HomeUsersPresenter
}