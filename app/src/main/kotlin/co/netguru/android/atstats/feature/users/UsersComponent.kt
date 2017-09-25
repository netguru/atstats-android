package co.netguru.android.atstats.feature.users

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface UsersComponent {

    fun getPresenter() : UsersPresenter
}