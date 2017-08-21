package co.netguru.android.socialslack.common.customTheme

import android.support.v7.app.AppCompatActivity
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.theme.ThemeOption


abstract class CustomThemeActivity: AppCompatActivity() {

    val component by lazy { App.getApplicationComponent(this).plusCustomThemeComponent() }
    private val themeComponent by lazy { component.getThemeController() }

    fun setTheme() {
        setTheme(if (themeComponent.getThemeSync() == ThemeOption.COLOURFUL) R.style.AppThemeColourful else R.style.AppThemeNetguru)
    }
}