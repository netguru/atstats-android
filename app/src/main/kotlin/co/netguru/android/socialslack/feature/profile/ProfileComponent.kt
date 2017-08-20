package co.netguru.android.socialslack.feature.profile

import co.netguru.android.socialslack.app.scope.FragmentScope
import co.netguru.android.socialslack.data.theme.ThemeController
import dagger.Subcomponent

@FragmentScope
@Subcomponent
interface ProfileComponent {

    fun getPresenter(): ProfilePresenter

    fun getThemeController(): ThemeController
}