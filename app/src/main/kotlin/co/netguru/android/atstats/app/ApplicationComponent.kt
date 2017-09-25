package co.netguru.android.atstats.app

import co.netguru.android.atstats.common.customTheme.CustomThemeComponent
import co.netguru.android.atstats.data.room.DatabaseModule
import co.netguru.android.atstats.feature.login.LoginComponent
import co.netguru.android.atstats.feature.splash.SplashComponent

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkModule::class, ApiModule::class,
        SessionRepositoryModule::class, DatabaseModule::class, LocalRepositoryModule::class))
internal interface ApplicationComponent {

    fun getDebugMetricsHelper(): DebugMetricsHelper

    fun userComponentBuilder(): UserComponent.Builder

    fun plusCustomThemeComponent(): CustomThemeComponent

    fun plusLoginComponent(): LoginComponent

    fun plusSplashComponent(): SplashComponent

    fun userComponentRestorer(): UserComponentRestorer
}