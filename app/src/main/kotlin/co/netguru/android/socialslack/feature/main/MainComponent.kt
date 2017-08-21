package co.netguru.android.socialslack.feature.main

import co.netguru.android.socialslack.app.scope.ActivityScope
import co.netguru.android.socialslack.data.theme.ThemeController
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface MainComponent {

    fun getThemeController(): ThemeController
}