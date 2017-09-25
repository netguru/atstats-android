package co.netguru.android.atstats.common.customTheme

import co.netguru.android.atstats.data.theme.ThemeController
import javax.inject.Inject


class CustomThemePresenterImpl @Inject constructor(themeController: ThemeController)
    : CustomThemePresenter<CustomThemeContract.View>(themeController) {
}