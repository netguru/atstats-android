package co.netguru.android.slacksocial.feature.login

import co.netguru.android.slacksocial.app.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent()
interface LoginComponent {

    fun inject(fragment : LoginFragment)

    fun getPresenter() : LoginPresenter
}