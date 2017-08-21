package co.netguru.android.socialslack.common.customTheme

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.app.App
import co.netguru.android.socialslack.data.theme.ThemeController
import co.netguru.android.socialslack.data.theme.ThemeOption
import javax.inject.Inject


abstract class CustomThemeActivity: AppCompatActivity() {

    val component by lazy { App.getApplicationComponent(this).plusCustomThemeComponent() }

    @Inject
    lateinit var themeController: ThemeController

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        setTheme()
        super.onCreate(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        setTheme()
        super.onCreate(savedInstanceState, persistentState)
    }

    private fun inject() {
        component.inject(this)
    }

    private fun setTheme() {
        setTheme(if (themeController.getThemeSync() == ThemeOption.COLOURFUL) R.style.AppThemeColourful else R.style.AppThemeNetguru)
    }
}