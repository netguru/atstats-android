package co.netguru.android.socialslack.app

import co.netguru.android.socialslack.feature.login.LoginComponent
import co.netguru.android.socialslack.feature.splash.SplashComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkModule::class, ApiModule::class,
        LocalRepositoryModule::class))
interface ApplicationComponent {

    fun plusLoginComponent(): LoginComponent

    fun plusSplashComponent(): SplashComponent
}