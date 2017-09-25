package co.netguru.android.atstats.feature.splash

import co.netguru.android.atstats.app.scope.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface SplashComponent {

    fun inject(splashActivity: SplashActivity)

    fun getPresenter() : SplashPresenter
}