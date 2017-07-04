package co.netguru.android.slacksocial.app

import co.netguru.android.slacksocial.feature.login.LoginComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun plusLoginComponent() : LoginComponent
}