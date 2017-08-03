package co.netguru.android.socialslack.feature.users.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface UsersProfileComponent {

    fun getPresenter() : UsersProfilePresenter
}