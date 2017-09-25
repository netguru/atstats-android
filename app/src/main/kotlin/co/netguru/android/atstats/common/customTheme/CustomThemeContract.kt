package co.netguru.android.atstats.common.customTheme

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView


interface CustomThemeContract {

    interface View : MvpView

    interface Presenter<V : View> : MvpPresenter<V> {

        fun showColourfulTheme(): Boolean
    }
}