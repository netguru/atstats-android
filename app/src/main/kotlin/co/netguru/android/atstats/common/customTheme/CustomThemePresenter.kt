package co.netguru.android.atstats.common.customTheme

import co.netguru.android.atstats.data.theme.ThemeController
import co.netguru.android.atstats.data.theme.ThemeOption
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter


abstract class CustomThemePresenter<V: CustomThemeContract.View> constructor(private val themeController: ThemeController) : MvpNullObjectBasePresenter<V>(),
        CustomThemeContract.Presenter<V> {

    override fun showColourfulTheme(): Boolean =
            themeController.getThemeSync() == ThemeOption.COLOURFUL
}