package co.netguru.android.socialslack.common.customTheme

import co.netguru.android.socialslack.app.scope.ActivityScope
import co.netguru.android.socialslack.data.theme.ThemeController
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface CustomThemeComponent {

    fun getThemeController(): ThemeController
}