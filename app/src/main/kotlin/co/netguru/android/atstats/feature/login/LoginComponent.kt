package co.netguru.android.atstats.feature.login

import co.netguru.android.atstats.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface LoginComponent {

    fun getPresenter() : LoginPresenter
}