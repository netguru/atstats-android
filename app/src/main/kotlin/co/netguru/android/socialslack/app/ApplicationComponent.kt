package co.netguru.android.socialslack.app

import co.netguru.android.socialslack.feature.channels.ChannelsComponent
import co.netguru.android.socialslack.feature.channels.profile.ChannelProfileComponent
import co.netguru.android.socialslack.feature.share.ShareComponent
import co.netguru.android.socialslack.feature.filter.FilterComponent
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

    fun plusChannelsComponent(): ChannelsComponent

    fun plusChannelProfileComponent(): ChannelProfileComponent

    fun plusChannelShareComponent(): ShareComponent

    fun plusFilterComponent(): FilterComponent
}