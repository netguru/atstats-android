package co.netguru.android.socialslack.feature.splash

import co.netguru.android.socialslack.app.scope.ActivityScope
import co.netguru.android.socialslack.data.theme.ThemeController
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface SplashComponent {

    fun inject(splashActivity: SplashActivity)

    fun getPresenter() : SplashPresenter

    fun getThemeController(): ThemeController
}