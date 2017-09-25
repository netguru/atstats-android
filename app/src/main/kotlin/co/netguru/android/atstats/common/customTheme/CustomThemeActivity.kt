package co.netguru.android.atstats.common.customTheme

import co.netguru.android.atstats.app.App


abstract class CustomThemeActivity :
        MvpCustomThemeActivity<CustomThemeContract.View, CustomThemeContract.Presenter<CustomThemeContract.View>>(),
        CustomThemeContract.View {

    private val component by lazy { App.getApplicationComponent(this).plusCustomThemeComponent() }

    override fun createPresenter(): CustomThemeContract.Presenter<CustomThemeContract.View> =
            component.getPresenter()
}