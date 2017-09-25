package co.netguru.android.atstats.common.customTheme

import android.os.Bundle
import co.netguru.android.atstats.R
import com.hannesdorfmann.mosby3.mvp.MvpActivity


abstract class MvpCustomThemeActivity<V : CustomThemeContract.View, P : CustomThemeContract.Presenter<V>> : MvpActivity<V, P>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()
    }

    open fun setTheme() {
        setTheme(if (getPresenter().showColourfulTheme()) R.style.AppThemeColourful else R.style.AppThemeNetguru)
    }
}