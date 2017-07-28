package co.netguru.android.socialslack.feature.home.users

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
internal interface HomeUsersComponent {

    fun getPresenter() : HomeUsersPresenter
}