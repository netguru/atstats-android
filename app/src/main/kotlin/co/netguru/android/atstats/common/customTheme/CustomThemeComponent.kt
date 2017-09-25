package co.netguru.android.atstats.common.customTheme

import co.netguru.android.atstats.app.scope.ActivityScope
import dagger.Subcomponent


@ActivityScope
@Subcomponent
interface CustomThemeComponent {

    fun getPresenter(): CustomThemePresenterImpl
}