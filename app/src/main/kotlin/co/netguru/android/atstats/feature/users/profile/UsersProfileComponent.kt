package co.netguru.android.atstats.feature.users.profile

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface UsersProfileComponent {

    fun getPresenter() : UsersProfilePresenter
}