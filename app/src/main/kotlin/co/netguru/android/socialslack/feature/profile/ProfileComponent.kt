package co.netguru.android.socialslack.feature.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface ProfileComponent {

    fun getPresenter(): ProfilePresenter
}