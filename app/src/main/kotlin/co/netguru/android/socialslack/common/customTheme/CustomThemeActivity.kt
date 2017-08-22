package co.netguru.android.socialslack.common.customTheme

import co.netguru.android.socialslack.app.App


abstract class CustomThemeActivity :
        MvpCustomThemeActivity<CustomThemeContract.View, CustomThemeContract.Presenter<CustomThemeContract.View>>(),
        CustomThemeContract.View {

    val component by lazy { App.getApplicationComponent(this).plusCustomThemeComponent() }

    override fun createPresenter(): CustomThemeContract.Presenter<CustomThemeContract.View> =
            component.getPresenter()
}