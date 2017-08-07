package co.netguru.android.socialslack.app

import co.netguru.android.socialslack.data.room.DatabaseModule
import co.netguru.android.socialslack.feature.login.LoginComponent
import co.netguru.android.socialslack.feature.splash.SplashComponent

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkModule::class, ApiModule::class,
        TokenRepositoryModule::class, DatabaseModule::class))
internal interface ApplicationComponent {

    fun userComponentBuilder(): UserComponent.Builder

    fun plusLoginComponent(): LoginComponent

    fun plusSplashComponent(): SplashComponent
}