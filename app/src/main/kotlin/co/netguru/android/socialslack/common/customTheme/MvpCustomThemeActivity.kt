package co.netguru.android.socialslack.common.customTheme

import android.os.Bundle
import android.os.PersistableBundle
import co.netguru.android.socialslack.R
import co.netguru.android.socialslack.data.theme.ThemeController
import co.netguru.android.socialslack.data.theme.ThemeOption
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import javax.inject.Inject


abstract class MvpCustomThemeActivity<V : MvpView, P : MvpPresenter<V>> : MvpActivity<V, P>() {

    @Inject
    lateinit var themeController: ThemeController

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        inject()
        setTheme()
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        setTheme()
        super.onCreate(savedInstanceState)
    }

    abstract fun inject()

    private fun setTheme() {
        setTheme(if (themeController?.getThemeSync() == ThemeOption.COLOURFUL) R.style.AppThemeColourful else R.style.AppThemeNetguru)
    }
}