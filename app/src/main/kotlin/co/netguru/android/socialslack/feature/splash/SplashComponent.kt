package co.netguru.android.socialslack.feature.splash

import co.netguru.android.socialslack.app.scope.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface SplashComponent {

    fun getPresenter() : SplashPresenter
}