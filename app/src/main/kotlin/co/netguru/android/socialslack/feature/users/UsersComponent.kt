package co.netguru.android.socialslack.feature.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface UsersComponent {

    fun getPresenter() : UsersPresenter
}