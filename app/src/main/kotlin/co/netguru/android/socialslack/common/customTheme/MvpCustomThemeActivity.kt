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


abstract class MvpCustomThemeActivity<V : CustomThemeContract.View, P : CustomThemeContract.Presenter<V>> : MvpActivity<V, P>() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setTheme()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()
    }

    open fun setTheme() {
        setTheme(if (getPresenter().showColourfulTheme()) R.style.AppThemeColourful else R.style.AppThemeNetguru)
    }
}