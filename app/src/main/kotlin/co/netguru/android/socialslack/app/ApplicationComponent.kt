package co.netguru.android.socialslack.app

import co.netguru.android.socialslack.feature.login.LoginComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun plusLoginComponent() : LoginComponent
}