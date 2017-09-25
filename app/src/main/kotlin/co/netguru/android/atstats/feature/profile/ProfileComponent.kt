package co.netguru.android.atstats.feature.profile

import co.netguru.android.atstats.app.scope.FragmentScope
import co.netguru.android.atstats.data.theme.ThemeController
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface ProfileComponent {

    fun getPresenter(): ProfilePresenter

    fun getThemeController(): ThemeController
}