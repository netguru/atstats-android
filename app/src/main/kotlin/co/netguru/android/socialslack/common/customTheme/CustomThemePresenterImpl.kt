package co.netguru.android.socialslack.common.customTheme

import co.netguru.android.socialslack.data.theme.ThemeController
import javax.inject.Inject


class CustomThemePresenterImpl @Inject constructor(themeController: ThemeController)
    : CustomThemePresenter<CustomThemeContract.View>(themeController) {
}