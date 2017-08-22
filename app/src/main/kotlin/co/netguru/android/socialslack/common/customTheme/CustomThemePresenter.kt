package co.netguru.android.socialslack.common.customTheme

import co.netguru.android.socialslack.data.theme.ThemeController
import co.netguru.android.socialslack.data.theme.ThemeOption
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter


abstract class CustomThemePresenter<V: CustomThemeContract.View> constructor(private val themeController: ThemeController) : MvpNullObjectBasePresenter<V>(),
        CustomThemeContract.Presenter<V> {

    override fun showColourfulTheme(): Boolean =
            themeController.getThemeSync() == ThemeOption.COLOURFUL

}