package co.netguru.android.socialslack.feature.login

import co.netguru.android.socialslack.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface LoginComponent {

    fun getPresenter() : LoginPresenter
}